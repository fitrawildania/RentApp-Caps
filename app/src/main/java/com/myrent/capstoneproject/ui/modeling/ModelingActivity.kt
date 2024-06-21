package com.myrent.capstoneproject.ui.modeling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.myrent.capstoneproject.MainActivity
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.databinding.ActivityModelingBinding

class ModelingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModelingBinding
    private val model: ModelViewModel by viewModels { ModelVmFactory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModelingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val rating = binding.Rating.text.toString().toIntOrNull() ?: 0
            val harga = binding.Harga.text.toString().toIntOrNull() ?: 0
            val kapasitas = binding.Kapasitas.text.toString().toIntOrNull() ?: 0
            val recommendation = model.recommend(userId = 1, itemId = 2)

            val resultIntent = Intent().apply {
                putExtra("rating", rating)
                putExtra("harga", harga)
                putExtra("kapasitas", kapasitas)
                putExtra("recommendationScore", recommendation)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Close the LoginActivity
    }
}
