package com.myrent.capstoneproject.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.myrent.capstoneproject.MainActivity
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.databinding.ActivityLoginBinding
import com.myrent.capstoneproject.ui.home.HomeFragment

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginbtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            //Validasi Email
            if (email.isEmpty()){
                binding.email.error = "Isi Email Terlebih Dahulu"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //Validasi Email yang tidak sesuai
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = "Email Tidak Valid"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.password.error = "Passwordnya di isi ya"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            if (password.length<6) {
                binding.password.error = "Password Kurang dari 6 Karakter"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase( email, password)
        }

    }

    private fun LoginFirebase( email:String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val name = it.displayName ?: "User"
                        Toast.makeText(this, "Login Berhasil $name", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeFragment::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}