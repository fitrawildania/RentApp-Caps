package com.myrent.capstoneproject.model

data class CarItem(
    val name: String,
    val spesifikasi: String,
    val namapemilik: String,
    val harga: String,
    val imagecar: String // Assuming this is a URL from the API
)
