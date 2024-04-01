package com.example.uts_pam

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts_pam.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmailRegister.text.toString()
            val pass = binding.edtPasswordRegister.text.toString()
            val username = binding.edtNamaPengguna.text.toString()
            val nick = binding.edtGithub.text.toString()
            val nim = binding.edtNim.text.toString()

            //Kondisi untuk validasi email
            if (email.isEmpty()) {
                binding.edtEmailRegister.error = "Fill the email!"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmailRegister.error = "Email is not valid!"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi text
            if (username.isEmpty()) {
                binding.edtNamaPengguna.error = "Fill your username!"
                return@setOnClickListener
            }

            if (nick.isEmpty()) {
                binding.edtGithub.error = "Fill your github nickname!"
                return@setOnClickListener
            }

            if (nim.isEmpty()) {
                binding.edtNim.error = "Fill your NIK!"
                return@setOnClickListener
            }

            //Validasi panjang nim
            if (nim.length < 9) {
                binding.edtNim.error = "Mininum NIM is 9!"
            }

            //Validasi password
            if (pass.isEmpty()) {
                binding.edtPasswordRegister.error = "Fill the password!"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi panjang password
            if (pass.length < 6) {
                binding.edtPasswordRegister.error = "Min Password is 6 char!"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email,pass)
        }

    }

    private fun RegisterFirebase(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}