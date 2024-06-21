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

data class Transaksi(
    val idTransaksi: String,
    val idPemilik: Int,
    val idPengguna: String,
    val tanggalSewa: String,
    val tanggalKembali: String,
    val durasiSewa: Int,
    val harga: String,
    val totalSewa: String,
    val metodePembayaran: String
)
