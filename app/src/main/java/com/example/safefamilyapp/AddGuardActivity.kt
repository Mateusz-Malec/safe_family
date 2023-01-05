package com.example.safefamilyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.safefamilyapp.models.Guard
import com.example.safefamilyapp.models.Register
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddGuardActivity : AppCompatActivity() {
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var passwordConfirm: TextInputEditText
    private lateinit var name: TextInputEditText
    private lateinit var surname: TextInputEditText
    private lateinit var phoneNumber: TextInputEditText
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_guard)

        username = findViewById(R.id.guard_etLogin)
        password = findViewById(R.id.guard_etPassword)
        passwordConfirm = findViewById(R.id.guard_etConfirmPassword)
        name = findViewById(R.id.guard_etName)
        surname = findViewById(R.id.guard_etSurname)
        phoneNumber = findViewById(R.id.guard_etPhoneNumber)
        btn = findViewById(R.id.registerGuardBtn)

        btn.setOnClickListener {
            val user = Guard(
                    username.text.toString(),
                    password.text.toString(),
                    phoneNumber.text.toString(),
                    name.text.toString(),
                    surname.text.toString()
                )

                    addGuard(user)
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)


        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun addGuard(guard: Guard) {
        val token = intent.getStringExtra("tokenForGuard")

        GlobalScope.launch {
            val apiService = RestApiService()
            apiService.addGuard("Bearer $token", guard) {
                if (it == null) {
                    Log.e("Register Guard Error", "Błąd")
                } else {
                    //Toast.makeText(this@AddGuardActivity, "Zarejestrowano", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}