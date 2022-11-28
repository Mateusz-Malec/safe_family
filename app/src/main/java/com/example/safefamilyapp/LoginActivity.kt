package com.example.safefamilyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class LoginActivity : AppCompatActivity() {
    lateinit var etMail: EditText
    lateinit var etPass: EditText
    lateinit var loginButton: Button
    lateinit var loading: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etMail = findViewById(R.id.login_etMail)
        etPass = findViewById(R.id.login_etPassword)
        loginButton = findViewById(R.id.loginButton)
        loading = findViewById(R.id.loading)

        // disable login button unless both username / password is valid
        //loginButton.isEnabled =  isPasswordValid(etPass.text)

        etPass.addTextChangedListener {
                loginButton.isEnabled  = isPasswordValid(etPass.text)
        }
        loginButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            if (etMail.text.contentEquals("admin") and etPass.text.contentEquals("admin")) {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "błąd", Toast.LENGTH_SHORT).show()
            }
        }


    }

    //private fun isDataValid(username: String, password: String): Boolean {
   //     return isUserNameValid(username) //and isPasswordValid(password)
   // }

    fun login(username: String, password: String) {

        val result = Login(username, password)
        //isUserNameValid(username)
        //isPasswordValid(password)
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: Editable): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: Editable): Boolean {
        return password.length > 4
    }
}