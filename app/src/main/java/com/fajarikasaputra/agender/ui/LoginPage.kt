package com.fajarikasaputra.agender.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.fajarikasaputra.agender.R
import com.fajarikasaputra.agender.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth


class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding

    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)

//        setContentView(R.layout.activity_login_page)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, SignUpPahe::class.java)
            startActivity(intent)
        }

        binding.btnSignin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            if (email.isEmpty()) {
                binding.emailLogin.error = "Email harus diisi "
                binding.emailLogin.requestFocus()
                return@setOnClickListener

            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLogin.error = "Email tidak valid"
                binding.emailLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordLogin.error = "Password harus diisi"
                binding.passwordLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.passwordLogin.error = "Password minimal 6 karakter"
                binding.passwordLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomepageActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}