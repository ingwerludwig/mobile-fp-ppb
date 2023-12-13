package com.example.countlories.viewmodel.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat
import kotlin.math.roundToInt

class CalculatorViewModel : ViewModel() {

    private val _idealWeight = MutableLiveData<Double>()
    val idealWeight : LiveData<Double> = _idealWeight

    private val _bodyMassIndex = MutableLiveData<Double>()
    val bodyMassIndex : LiveData<Double> = _bodyMassIndex

    fun calculateBMI(gender: String, weight: Double, height: Double){
        val bmi = ( weight / (height*height) ) * 10000
        val format = DecimalFormat("#.#")
        val formattedNumber = format.format(bmi)
        _bodyMassIndex.value = formattedNumber.toDouble()

        val weightIdeal = calculateWeightIdeal(gender, height)
        _idealWeight.value = format.format(weightIdeal).toDouble()
    }

    private fun calculateWeightIdeal(gender: String, height: Double) : Double{
        var weightIdeal = 0.0

        if (gender == "Laki-laki"){
            weightIdeal = (height - 100) - ((height - 100) * 10 / 100)
        } else if (gender == "Perempuan"){
            weightIdeal = (height - 100) - ((height - 100) * 15 / 100)
        }

        return weightIdeal
    }
}