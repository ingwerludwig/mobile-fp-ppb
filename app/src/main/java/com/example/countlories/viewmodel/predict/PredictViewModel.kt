package com.example.countlories.viewmodel.predict

import androidx.lifecycle.*
import com.example.countlories.data.response.DetailMenu
import com.example.countlories.data.response.GetAllMenu
import com.example.countlories.data.response.PredictResponse
import com.example.countlories.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PredictViewModel (private val repository: Repository) : ViewModel() {

    val isLoading: LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError
    val predictData : LiveData<PredictResponse> = repository.predictData
    val getAllMenuData : LiveData<GetAllMenu> = repository.getAllMenuData
    val detailMenu : LiveData<DetailMenu> = repository.detailMenu

    private val _hasTakenPhoto = MutableLiveData<Boolean>()

    fun predictImage(image: MultipartBody.Part){
        viewModelScope.launch {
            repository.predictImage(image)
        }

    }

    fun getDetailMenu(id: String){
        viewModelScope.launch {
            repository.getDetailMenu(id)
        }
    }

    fun getAllMenu(){
        viewModelScope.launch {
            repository.getAllMenu()
        }
    }

    fun setHasTakenPhoto(Taken: Boolean = false){
        _hasTakenPhoto.value = Taken
    }
}