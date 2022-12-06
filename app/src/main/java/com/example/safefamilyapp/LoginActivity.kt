package com.example.safefamilyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.safefamilyapp.models.Login
import com.google.android.material.textfield.TextInputEditText
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var loading: ProgressBar

    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.loginButton)
        loading = findViewById(R.id.loading)

        username = findViewById(R.id.login_tietUsername)
        password = findViewById(R.id.login_tietPassword)

        // disable login button unless both username / password is valid
        //loginButton.isEnabled =  isPasswordValid(etPass.text)



        password.addTextChangedListener {
            loginButton.isEnabled = password.text?.let { it1 -> isPasswordValid(it1) } == true
        }

        loginButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            val login = Login(username.text.toString(), password.text.toString())

            //loginWithApi(login)
            loginWithoutApi()

        }


    }

    //private fun isDataValid(username: String, password: String): Boolean {
    //     return isUserNameValid(username) //and isPasswordValid(password)
    // }

    private fun loginWithApi(login: Login) {
        val apiService = RestApiService()

        apiService.loginUser2(login) {
            if (it == null) {
                Toast.makeText(this, "Błąd", Toast.LENGTH_SHORT).show()
            } else {
                finish()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginWithoutApi (){
        finish()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
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
        return password.length > 3
    }
}