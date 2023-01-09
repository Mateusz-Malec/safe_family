package com.example.safefamilyapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var loading: ProgressBar

    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    private lateinit var toRegisterActivity: TextView
    private lateinit var forgotpassw: TextView

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        loginButton = findViewById(R.id.loginButton)
        loading = findViewById(R.id.loading)

        username = findViewById(R.id.login_tietUsername)
        password = findViewById(R.id.login_tietPassword)

        forgotpassw = findViewById(R.id.forgotpassw)
        toRegisterActivity = findViewById(R.id.loginToRegister)


        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE)


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

            loginWithApi(login)
            //loginWithoutApi()

        }

        forgotpassw.setOnClickListener {
            finish()
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        toRegisterActivity.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    //private fun isDataValid(username: String, password: String): Boolean {
    //     return isUserNameValid(username) //and isPasswordValid(password)
    // }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loginWithApi(login: Login) {
        val apiService = RestApiService()

        apiService.loginUser2(login) {
            if (it == null) {

                Toast.makeText(this, "Błąd", Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

                val x = Gson().toJson(it)
                //Toast.makeText(this, x, Toast.LENGTH_LONG).show()

                preferences.edit().putString("TOKEN", it.Token).apply()
                preferences.edit().putString("REFRESH_TOKEN", it.RefreshToken.Token).apply()
                preferences.edit().putString("ROLE", it.Role).apply()

                finish()
                if (it.Role == "User") {
                    val intent = Intent(this, HomeActivity::class.java)
                    //intent.putExtra("token", it.Token)
                    //val tokenForUser = Intent(this, AddDeviceActivity::class.java)
                    //val tokenForGuard = Intent(this, AddGuardActivity::class.java)
                    //tokenForUser.putExtra("tokenForUser", it.Token)
                    //tokenForGuard.putExtra("tokenForGuard", it.Token)
                    //intent.putExtra("refreshToken", it.RefreshToken.Token)
                    //intent.putExtra("token2", it.toString())
                    startActivity(intent)
                } else if (it.Role == "Guard") {
                    val intent = Intent(this, GuardActivity::class.java)
                    //intent.putExtra("token", it.Token)
                    //intent2.putExtra("tokenForGuard", it.Token)
                    //intent.putExtra("refreshToken", it.RefreshToken.Token)
                    //intent.putExtra("token2", it.toString())

                    //val tokenForDevice = Intent(this, GuardActivity::class.java)
                    //val tokenForGuard = Intent(this, AddGuardActivity::class.java)
                    //tokenForDevice.putExtra("tokenForDevice", it.Token)

                    //Toast.makeText(this, it.Token, Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
                //val intent2 = Intent(this, AddGuardActivity::class.java)
                //intent.putExtra("token", it.Token)
                //intent2.putExtra("tokenForGuard", it.Token)
                //intent.putExtra("refreshToken", it.RefreshToken.Token)
                //intent.putExtra("token2", it.toString())

                //val json = Gson().toJson(it)

                Log.i("LoginActivity Response", x)

                //val intent2 = Intent(this, ProfileFragment::class.java)
                //intent2.putExtra("token", it.toString())

            }
        }
    }

    private fun loginWithoutApi() {
        finish()

        val token =
            "{Token=eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTUxMiIsInR5cCI6IkpXVCJ9.eyJMb2dpbiI6InRlc3QxIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiVXNlciIsImV4cCI6MTY3Mjg2Mjc5NX0.Rf93Pp4mzW8EnlOj7yW81wUphsqb7k3Er3nS_oIVFj9rjR7vnA43OXq7_Rl-NCeX4rdxGDTwgGC3E47tv40NWg, status=1.0, Role=User, RefreshToken={Token=let3HE8pIFxE8NZLWuMF810LMnR64+2xAnyfMFk2Ca/F1Qwxr81PY183+c6/jU4Bv8gOhEsArvNIKvRp2UIxvg==, CreatedDate=2023-01-04T20:16:35.0703626+01:00, ValidTo=2023-01-05T20:16:35.0703654+01:00}}"
        val x = Gson().toJson(token)
        Toast.makeText(this, x, Toast.LENGTH_LONG).show()
        //val intent = Intent(this, HomeActivity::class.java)
        //intent.putExtra("token", token)
        //startActivity(intent)
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