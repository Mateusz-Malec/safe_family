package com.example.safefamilyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    lateinit var etMail: EditText
    lateinit var etPass: EditText
    lateinit var loginButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etMail = findViewById(R.id.login_etMail)
        etPass = findViewById(R.id.login_etPassword)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            if (etMail.text.contentEquals("admin") and etPass.text.contentEquals("admin")) {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "błąd",Toast.LENGTH_SHORT).show()
            }
        }


    }
}