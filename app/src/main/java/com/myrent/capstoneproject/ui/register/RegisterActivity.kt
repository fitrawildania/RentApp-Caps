package com.myrent.capstoneproject.ui.register
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.myrent.capstoneproject.databinding.ActivityRegisterBinding
import com.myrent.capstoneproject.ui.login.LoginActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://login-and-register-177a4-default-rtdb.firebaseio.com/").reference

        binding.registerBtn.setOnClickListener {
            val email = binding.rgtEmail.text.toString()
            val password = binding.password.text.toString()
            val name = binding.username.text.toString()

            if(name.isEmpty()){
                binding.username.error = "Isi Username Terlebih dahulu"
                binding.username.requestFocus()
                return@setOnClickListener
            }

            //Validasi Email
            if (email.isEmpty()){
                binding.rgtEmail.error = "Isi Email Terlebih Dahulu"
                binding.rgtEmail.requestFocus()
                return@setOnClickListener
            }


            //Validasi Email yang tidak sesuai
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.rgtEmail.error = "Email Tidak Valid"
                binding.rgtEmail.requestFocus()
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

            RegisterFirebase(name, email, password)
        }
    }
    private fun RegisterFirebase(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userId = it.uid
                        val userMap = mapOf(
                            "name" to name,
                            "email" to email
                        )
                        database.child("users").child(userId).setValue(userMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Register Berhasil Silahkan Login", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this, "Failed to save user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


