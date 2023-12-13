package com.example.countlories.ui.community

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.countlories.R
import com.example.countlories.databinding.ActivityUploadForumBinding
import com.example.countlories.utils.createCustomTempFile
import com.example.countlories.utils.factory.ViewModelFactory
import com.example.countlories.utils.uriToFile
import com.example.countlories.viewmodel.forum.ForumViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadForumBinding

    private lateinit var factory: ViewModelFactory
    private val forumViewModel: ForumViewModel by viewModels { factory }

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS){
            if (!allPermissionsGranted()){
                Toast.makeText(this, "Tidak mendapatkan permission", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()){
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        transition()
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        factory = ViewModelFactory.getInstance(this)

        forumViewModel.postForumData.observe(this){ forum ->
            if (forum.status){
                Toast.makeText(this, forum.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, CommunityActivity::class.java))
            }
        }

        forumViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    private fun setupAction(){
        binding.btnCamera.setOnClickListener {
            startCamera()
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnUpload.setOnClickListener {
            postForum()
        }
    }

    private fun postForum(){
        forumViewModel.getUserData().observe(this){ user ->
            if (getFile != null){
                val file = getFile as File
                val title = binding.edtTitleStory.text.toString().toRequestBody("text/plain".toMediaType())
                val description = binding.edtDescStory.text.toString().toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultiPart : MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )

                forumViewModel.postForum(
                    user.token,
                    imageMultiPart,
                    title,
                    description
                )
            } else {
                Toast.makeText(this, "Lengkapi semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI : Uri = FileProvider.getUriForFile(
                this@UploadForumActivity,
                "com.example.countlories",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"

        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK){
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                getFile = file
                binding.ivAddStory.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == RESULT_OK){
            val selectedImage = result.data?.data as Uri

            selectedImage.let { uri ->
                val myFile = uriToFile(uri, this@UploadForumActivity)
                getFile = myFile
                binding.ivAddStory.setImageURI(uri)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
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