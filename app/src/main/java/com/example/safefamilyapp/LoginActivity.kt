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
import java.io.IOException

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

        //val api = "https://665e-91-237-73-2.eu.ngrok.io/api/Login?Login=Dorkman&Password=okon1234"


        etPass.addTextChangedListener {
            loginButton.isEnabled = isPasswordValid(etPass.text)
        }

        loginButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            val login = Login(etMail.text.toString(), etPass.text.toString())

            loginWithoutApi(login)

        }


    }

    //private fun isDataValid(username: String, password: String): Boolean {
    //     return isUserNameValid(username) //and isPasswordValid(password)
    // }

    private fun login(login: Login) {
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

    private fun loginWithoutApi (login: Login){
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