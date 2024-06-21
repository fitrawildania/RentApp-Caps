package com.myrent.capstoneproject.ui.modeling

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModelVmFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ModelViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
