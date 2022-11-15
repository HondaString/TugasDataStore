package com.navigation.latihan.githubuserapp.DataStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DarkNightModelFactory (private val pref: Darknight) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DarkNightView::class.java)) {
            return DarkNightView(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}