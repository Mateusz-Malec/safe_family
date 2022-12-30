package com.example.safefamilyapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.safefamilyapp.models.Login
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var loading: ProgressBar

    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    private lateinit var toRegisterActivity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.loginButton)
        loading = findViewById(R.id.loading)

        username = findViewById(R.id.login_tietUsername)
        password = findViewById(R.id.login_tietPassword)

        toRegisterActivity = findViewById(R.id.loginToRegister)

        // disable login button unless both username / password is valid
        //loginButton.isEnabled =  isPasswordValid(etPass.text)


        password.addTextChangedListener {
            loginButton.isEnabled = password.text?.let { it1 -> isPasswordValid(it1) } == true
            if (password.text?.let { it1 -> isPasswordValid(it1) } == false) password.error =
                getString(R.string.invalid_password)
        }

        loginButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            val login = Login(username.text.toString(), password.text.toString())

            //loginWithApi(login)
            loginWithoutApi()

        }

        toRegisterActivity.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
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

    private fun loginWithoutApi() {
        finish()
        val intent = Intent(this, GuardActivity::class.java)
        startActivity(intent)
    }

    // A placeholder username validation check
    private fun isEmailValid(email: Editable): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: Editable): Boolean {
        return password.length > 5 /*password.toString().toRegex().matches("[A-Z]{1,}[0-9]{1,}[a-z]"))*/
    }
}