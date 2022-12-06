package com.example.safefamilyapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.safefamilyapp.R
import com.example.safefamilyapp.RestApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    lateinit var refresh: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        refresh = rootView.findViewById(R.id.tokenRefresh)

        refresh.setOnClickListener {
            refresh()
        }

        return rootView
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun refresh() {

        GlobalScope.launch {
            val apiService = RestApiService()
            val token = apiService.responseBody
            apiService.refreshToken(token) {
                if (it == null) {
                    //Log.e("Register Error", "Błąd")
                } else {
                    //Toast.makeText(this, "Zarejestrowano", Toast.LENGTH_SHORT).show()
                    Log.d("Refresh", it.toString())
                }
            }
        }
    }

}