package com.myrent.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.data.RetrofitClient
import com.myrent.capstoneproject.model.CarItem
import com.myrent.capstoneproject.model.OwnersItem
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _carItems = MutableLiveData<List<CarItem>>()
    val carItems: LiveData<List<CarItem>> get() = _carItems

    private val _ownersItems = MutableLiveData<List<OwnersItem>>()
    val ownersItems: LiveData<List<OwnersItem>> get() = _ownersItems

    init {
        fetchCarsItems("")
        fetchOwnersItems()
    }

    fun fetchCarsItems(filter: String) {
        viewModelScope.launch {
            try {
                val apiCarItems = RetrofitClient.apiService.getCars(filter)
                val carItemsWithImages = apiCarItems.map { apiCarItem ->
                    val imageResId = getImageResourceForCar(apiCarItem.merk_kendaraan)
                    CarItem(
                        merk_kendaraan = apiCarItem.merk_kendaraan,
                        Transmisi = apiCarItem.Transmisi,
                        id_pemilik = apiCarItem.id_pemilik,
                        harga_per_hari = apiCarItem.harga_per_hari,
                        kapasitas = apiCarItem.kapasitas,
                        imagecar = imageResId
                    )
                }
                _carItems.postValue(carItemsWithImages)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getImageResourceForCar(carName: String): Int {
        return when (carName) {
            "Car Name 1" -> R.drawable.sample
            else -> R.drawable.sample // Default image
        }
    }

    fun fetchOwnersItems() {
        viewModelScope.launch {
            try {
                val owners = RetrofitClient.apiService.getOwners()
                _ownersItems.postValue(owners)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
