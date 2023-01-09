package com.example.safefamilyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.safefamilyapp.models.Register
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var mail: TextInputEditText
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        mail=findViewById(R.id.resetPass_etMail)
        button=findViewById(R.id.btnResetPasswd)

        button.setOnClickListener {
            resetPassword(mail.text.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun resetPassword(mail: String) {

        GlobalScope.launch {
            val apiService = RestApiService()
            apiService.resetPassword(mail) {
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