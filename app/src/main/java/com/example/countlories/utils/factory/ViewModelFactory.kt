package com.example.countlories.utils.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countlories.data.response.PredictResponse
import com.example.countlories.repository.Repository
import com.example.countlories.utils.Injection
import com.example.countlories.viewmodel.authentication.LoginViewModel
import com.example.countlories.viewmodel.authentication.RegisterViewModel
import com.example.countlories.viewmodel.forum.ForumViewModel
import com.example.countlories.viewmodel.main.MainViewModel
import com.example.countlories.viewmodel.predict.PredictViewModel
import com.example.countlories.viewmodel.profile.ProfileViewModel
import com.example.countlories.viewmodel.tips.TipsViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ForumViewModel::class.java) -> {
                ForumViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TipsViewModel::class.java) -> {
                TipsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PredictViewModel::class.java) -> {
                PredictViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }

}