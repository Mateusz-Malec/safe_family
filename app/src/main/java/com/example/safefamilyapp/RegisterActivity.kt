package com.example.safefamilyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.safefamilyapp.models.Register
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var username: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var passwordConfirm: TextInputEditText
    private lateinit var name: TextInputEditText
    private lateinit var surname: TextInputEditText
    private lateinit var phoneNumber: TextInputEditText
    private lateinit var btn: Button
    private lateinit var toLoginActivity: TextView

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
        toLoginActivity = findViewById(R.id.registerToLogin)

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

        toLoginActivity.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun register(user: Register) {

        GlobalScope.launch {
            val apiService = RestApiService()
            apiService.registerUser(user) {
                if (it == null) {
                    //Log.e("Register Error", "Błąd")
                } else {
                    //Toast.makeText(this, "Zarejestrowano", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}