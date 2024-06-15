package com.myrent.capstoneproject.data

import com.myrent.capstoneproject.ui.home.HomeAdapter
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("cars")
    suspend fun getCars(@Query("filter") filter: String): List<HomeAdapter.CarItem>
}
