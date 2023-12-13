package com.example.countlories.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.countlories.repository.Repository

private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideRepository(context: Context) : Repository{
        val preferences = UserPreference.getInstance(context.datastore)
        return Repository.getInstance(preferences)
    }
}