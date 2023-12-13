package com.example.countlories.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraInfo
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.TorchState
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.countlories.R
import com.example.countlories.databinding.ActivityCameraBinding
import com.example.countlories.ui.home.MainActivity
import com.example.countlories.utils.createFile
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.viewmodel.predict.PredictViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.util.*

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    private lateinit var factory: ViewModelFactory
    private val predictViewModel: PredictViewModel by viewModels { factory }

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private var cameraInfo: CameraInfo? = null
    private var cameraControl: CameraControl? = null
    private var linearZoom = 0f
    private val zoomState = MutableLiveData<Float>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupPermissions()
        setupViewModel()
        showAlert()
        setupAction()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupPermissions(){
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        factory = ViewModelFactory.getInstance(this)

        predictViewModel.isLoading.observe(this){loading ->
            if (loading){
                showLoading(true)
            } else {
                showLoading(false)
            }
        }

        predictViewModel.isError.observe(this){ error ->
            isError(error)
        }

    }

    private fun setupAction(){
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.captureImage.setOnClickListener {
            takePhoto()
        }
        binding.flashImage.setOnClickListener {
            toggleTorch()
        }
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .build()

            imageCapture = ImageCapture.Builder().apply {
                setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            }.build()

            val camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture
            )

            binding.viewFinder.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

            cameraInfo = camera.cameraInfo
            cameraControl = camera.cameraControl

            setTorchStateObserver()
            setZoomStateObserver()

            cameraInfo?.zoomState?.observe(this){ state ->
                zoomState.value = state?.linearZoom
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun setTorchStateObserver(){
        cameraInfo?.torchState?.observe(this){ state ->
            if (state == TorchState.ON){
                binding.flashImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_flash_off_24
                    )
                )
            } else {
                binding.flashImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_flash_on_24
                    )
                )
            }

        }
    }

    private fun setZoomStateObserver(){
        zoomState.observe(this){ state ->
            Log.d("State zoom: ", "$state")
        }
    }

    private fun toggleTorch(){
        if (cameraInfo?.torchState?.value == TorchState.ON){
            cameraControl?.enableTorch(false)
        } else {
            cameraControl?.enableTorch(true)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode){
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (linearZoom <= 0.9){
                    linearZoom += 0.1f
                }
                cameraControl?.setLinearZoom(linearZoom)
                true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (linearZoom >= 0.1) {
                    linearZoom -= 0.1f
                }
                cameraControl?.setLinearZoom(linearZoom)
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    private fun takePhoto() {

        val imageCapture = imageCapture ?: return

        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                    val requestImageFile = photoFile.asRequestBody("image/jpeg".toMediaType())
                    val imageMultiPart : MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photo",
                        photoFile.name,
                        requestImageFile
                    )

                    predictViewModel.predictImage(imageMultiPart)

                    Toast.makeText(this@CameraActivity, "Berhasil", Toast.LENGTH_SHORT).show()

                    predictViewModel.setHasTakenPhoto(true)

                    startActivity(Intent(this@CameraActivity, ReviewActivity::class.java))
                    transition()
                    finish()
                }
            }
        )
    }

    private fun showAlert() {
        val alert = AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("Pastikan menggunakan mode portrait saat mengambil gambar")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alert.show()

        @Suppress("DEPRECATION")
        val handler = Handler()
        val runnable = Runnable {
            if (alert.isShowing) {
                alert.dismiss()
            }
        }

        val delayMillis = 5000L
        handler.postDelayed(runnable, delayMillis)
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun isError(isError: Boolean){
        if (isError){
            Toast.makeText(this, "Error occurred, please try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun transition() {
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit)
    }

    companion object {

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10

    }


}