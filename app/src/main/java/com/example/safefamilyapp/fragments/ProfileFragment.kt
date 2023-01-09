package com.example.safefamilyapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.safefamilyapp.AddDeviceActivity
import com.example.safefamilyapp.AddGuardActivity
import com.example.safefamilyapp.R
import com.example.safefamilyapp.RestApiService
import com.example.safefamilyapp.models.RefreshToken
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private lateinit var refresh: ImageView
    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var email: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var btn: Button

    lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        refresh = rootView.findViewById(R.id.tokenRefresh)
        name = rootView.findViewById(R.id.profileName)
        surname = rootView.findViewById(R.id.profileSurname)
        email = rootView.findViewById(R.id.profileEmail)
        phoneNumber = rootView.findViewById(R.id.profilePhone)
        btn = rootView.findViewById(R.id.btn_verifyDevice)


        //val token = requireActivity().intent.extras!!.getString("token")
        //val refreshToken = requireActivity().intent.extras!!.getString("refreshToken")
        //val fullResponse2 = requireActivity().intent.extras!!.getString("token2")

        preferences = requireActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN",null)
        val refreshToken = preferences.getString("REFRESH_TOKEN",null)

        if (token != null) {
            displayProfile(token)
        }

        btn.setOnClickListener {

            activity?.let{
                val intent = Intent (it, AddDeviceActivity::class.java)
                intent.putExtra("tokenForUser", token)
                it.startActivity(intent)
            }
        }

        refresh.setOnClickListener {
            //val token = apiService.responseBody
            //val fullResponse = requireActivity().intent.extras!!.getString("token")
            //val x = Gson().toJson(fullResponse2)
            //email.text = fullResponse
            //name.text = fullResponse2

            /*if (fullResponse != null) {
                displayProfile(fullResponse)
                Log.i("DisplayProfile", fullResponse)
            }

            if (fullResponse2 != null) {
                Log.d("ProfileFragment json", fullResponse2)
                //refresh(fullResponse2)
            }*/
            if (token != null && refreshToken!=null) {
                refresh(token,refreshToken)
            }
            //Snackbar.make(it, x, Snackbar.LENGTH_LONG).show()
            //Log.d("json", json.toString())
            //Log.d("token", token)
        }

        return rootView
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun refresh(token: String, refreshToken: String) {

        GlobalScope.launch {
            val apiService = RestApiService()
            //val token = apiService.responseBody

            //var gson = Gson()

            //val x = Gson().toJson(token)
            //val json = JSONObject(x)

            //val fullResponse = activity?.intent?.extras?.getString("token")
            //val json = Gson().toJson(fullResponse)
            apiService.refreshToken(token, refreshToken) {
                if (it == null) {
                    Log.e("Refresh Error", "Błąd")
                    //Log.e("Error json", json.toString())
                    //Log.e("Error json", json)
                    //Log.e("Error json", fullResponse.toString())
                } else {
                    //Toast.makeText(this, "Zarejestrowano", Toast.LENGTH_SHORT).show()
                    Log.d("Refresh", it.toString())
                }
            }
        }
    }

    private fun displayProfile(token: String) {
        val apiService = RestApiService()

        apiService.displayProfile("Bearer $token") {
            if (it == null) {
                //Log.e("Register Error", "Błąd")
            } else {
                name.text = it.Name
                surname.text = it.SurName
                email.text = it.Email
                phoneNumber.text=it.PhoneNumber

                //Log.i("ProfileInfi", it.toString())
            }
        }
    }

}