package com.myrent.capstoneproject.ui.welcome

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.myrent.capstoneproject.databinding.ActivityWelcomeBinding
import com.myrent.capstoneproject.ui.login.LoginActivity
import com.myrent.capstoneproject.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding :ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        // Gunakan binding setelah diinisialisasi
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginbtn.setOnClickListener {
             startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerbtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}