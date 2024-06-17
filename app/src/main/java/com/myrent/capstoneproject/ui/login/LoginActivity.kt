package com.myrent.capstoneproject.ui.login

import android.content.ContentProviderClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.myrent.capstoneproject.MainActivity
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.databinding.ActivityLoginBinding
import com.myrent.capstoneproject.ui.home.HomeFragment

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 9001  // Request code for Google Sign-In
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        // Temukan tombol Google Sign-In
        val btnGoogle: ImageView = findViewById(R.id.btngoogle)

        // Setel OnClickListener untuk tombol Google Sign-In
        btnGoogle.setOnClickListener {
            googlesignin()
        }

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
    private fun googlesignin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = FirebaseAuth.getInstance().currentUser
                    Toast.makeText(this, "Authentication Success.", Toast.LENGTH_SHORT).show()
                    // Update UI or navigate to another activity

                    val intent = Intent(this, HomeFragment::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
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