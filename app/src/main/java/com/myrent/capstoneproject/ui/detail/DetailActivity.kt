package com.myrent.capstoneproject.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Terima data dari HomeFragment
        val carName = intent.getStringExtra("carName") ?: "N/A"
        val ownerName = intent.getStringExtra("ownerName") ?: "N/A"
        val about = intent.getStringExtra("about") ?: "N/A"
        val spesifikasi = intent.getStringExtra("spesifikasi") ?: "N/A"
        val seatCount = intent.getIntExtra("seatCount", 0)
        val carImageResId = intent.getIntExtra("carImageResId", R.drawable.car)

        // Set data ke tampilan
        binding.tvCarName.text = carName
        binding.tvOwnerName.text = ownerName
        binding.tvAbout.text = about
        binding.tvSeatCount.text = "$seatCount Kursi"
        binding.ivCar.setImageResource(carImageResId)

        // Tombol kembali
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}