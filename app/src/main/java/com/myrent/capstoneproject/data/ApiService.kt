package com.myrent.capstoneproject.data

import com.myrent.capstoneproject.model.CarItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("kendaraan")
    suspend fun getCars(@Query("filter") filter: String): List<CarItem>
}
