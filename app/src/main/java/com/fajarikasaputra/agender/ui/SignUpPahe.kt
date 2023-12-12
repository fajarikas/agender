package com.fajarikasaputra.agender.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.fajarikasaputra.agender.R
import com.fajarikasaputra.agender.databinding.ActivityLoginPageBinding
import com.fajarikasaputra.agender.databinding.ActivitySignUpPaheBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpPahe : AppCompatActivity() {
    lateinit var auth : FirebaseAuth

    lateinit var binding : ActivitySignUpPaheBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpPaheBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_up_pahe)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLoginhere.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()

            if (email.isEmpty()) {
                binding.emailRegister.error = "Email harus diisi "
                binding.emailRegister.requestFocus()
                return@setOnClickListener

            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailRegister.error = "Email tidak valid"
                binding.emailRegister.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordRegister.error = "Password harus diisi"
                binding.passwordRegister.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.passwordRegister.error = "Password minimal 6 karakter"
                binding.passwordRegister.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email, password)
        }

    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginPage::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}