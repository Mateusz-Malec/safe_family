package com.example.safefamilyapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

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
            val registerUser = Register(
                username.toString(),
                password.toString(),
                passwordConfirm.toString(),
                name.toString(),
                surname.toString(),
                mail.toString(),
                phoneNumber.toString()
            )
        }
    }
}