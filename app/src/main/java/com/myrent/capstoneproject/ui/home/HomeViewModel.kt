package com.myrent.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myrent.capstoneproject.data.RetrofitClient
import com.myrent.capstoneproject.model.CarItem
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _carItems = MutableLiveData<List<CarItem>>()
    val carItems: LiveData<List<CarItem>> get() = _carItems

    fun fetchCars(filter: String) {
        viewModelScope.launch {
            try {
                val items = RetrofitClient.apiService.getCars(filter)
                _carItems.postValue(items)
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}
