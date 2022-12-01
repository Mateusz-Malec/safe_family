package com.example.safefamilyapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safefamilyapp.models.Register
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var mail: EditText
    lateinit var password: EditText
    lateinit var passwordConfirm: EditText
    lateinit var name: EditText
    lateinit var surname: EditText
    lateinit var phoneNumber: EditText
    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.register_etLogin)
        mail = findViewById(R.id.register_etMail)
        password = findViewById(R.id.register_etPassword)
        passwordConfirm = findViewById(R.id.register_etConfirmPassword)
        name = findViewById(R.id.register_etName)
        surname = findViewById(R.id.register_etSurname)
        phoneNumber = findViewById(R.id.register_etPhoneNumber)
        btn = findViewById(R.id.registerButton)

        btn.setOnClickListener {
            val user = Register(
                username.text.toString(),
                password.text.toString(),
                passwordConfirm.text.toString(),
                name.text.toString(),
                surname.text.toString(),
                mail.text.toString(),
                phoneNumber.text.toString()
            )
            register(user)
        }
    }

    private fun register(user: Register) {

        GlobalScope.launch {
            val apiService = RestApiService()
            apiService.registerUser(user) {
                if (it == null) {
                    Log.e("Register Error", "Błąd")
                } else {
                    finish()
                }
            }
        }
    }
}