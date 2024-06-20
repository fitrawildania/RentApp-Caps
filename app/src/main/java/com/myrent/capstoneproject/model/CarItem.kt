package com.myrent.capstoneproject.model

data class CarItem(
    val merk_kendaraan: String,
    val Transmisi: String,
    val id_pemilik: String,
    val harga_per_hari: String,
    val kapasitas: Int,
    val imagecar: Int
)

data class OwnersItem(
    val id_pemilik: Int,
    val nama_pemilik: String,
    val alamat_pemilik: String,
    val usia: Int,
    val nmr_tlp: String,
    val email_pemilik: String
)