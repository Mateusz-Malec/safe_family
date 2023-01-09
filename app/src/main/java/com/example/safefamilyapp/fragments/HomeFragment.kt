package com.example.safefamilyapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safefamilyapp.GuardsAdapter
import com.example.safefamilyapp.R
import com.example.safefamilyapp.RestApiService
import com.example.safefamilyapp.models.Device
import com.example.safefamilyapp.models.GuardView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var id: String
    //private val list = ArrayList<GuardView>()
    //private val listOfAll = ArrayList<Device>()
    private val listOfAll = ArrayList<GuardView>()
    private val listOfLocations = ArrayList<LatLng>()
    lateinit var refresh: TextView
    lateinit var rv: RecyclerView

    lateinit var preferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        refresh = view.findViewById(R.id.refreshList)
        //id = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)

        //val listOfGuards = ArrayList<GuardView>()

        //val fullResponse = requireActivity().intent.extras!!.getString("token")
        //val fullResponse2 = requireActivity().intent.extras!!.getString("token")
        preferences = requireActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN", null)
        if (token != null) {
            getGuards(token)
        }
        rv = view.findViewById(R.id.listOfGuards)

        return view
    }


    private fun getGuards(token: String) {
        val apiService = RestApiService()
        apiService.getGuards("Bearer $token") {
            if (it == null) {
                Snackbar.make(requireView(), "Error", Snackbar.LENGTH_LONG).show()
                //Log.e("Register Error", "Błąd")
            } else {
                for (item in it) {
                    //list.add(item)
                    listOfAll.add(item)
                    Log.i("Guard item", item.toString())
                    //l.add(item)
                }
                Log.i("Lista", listOfAll.toString())

                val adapter = GuardsAdapter(listOfAll)
                //adapter.notifyDataSetChanged()
                rv.layoutManager = LinearLayoutManager(activity)
                rv.adapter = adapter

            }
        }

        //val adapter = GuardsAdapter(l)
        //rv.layoutManager = LinearLayoutManager(activity)
        //rv.adapter = adapter

    }


/*
    private fun getAll(token: String) {
        val apiService = RestApiService()
        apiService.getAll("Bearer $token") {
            if (it == null) {
                Snackbar.make(requireView(), "Error", Snackbar.LENGTH_LONG).show()
                //Log.e("Register Error", "Błąd")
            } else {
                for (item in it) {
                    //list.add(item)
                    listOfAll.add(item)
                    val latLng = LatLng(item.Latitude, item.Longtitute)
                    listOfLocations.add(latLng)
                    Log.i("Guard item", item.toString())
                    //l.add(item)
                }
                Log.i("Lista", listOfLocations.toString())

                val adapter = GuardsAdapter(listOfLocations)
                //adapter.notifyDataSetChanged()
                rv.layoutManager = LinearLayoutManager(activity)
                rv.adapter = adapter

            }
        }

        //val adapter = GuardsAdapter(l)
        //rv.layoutManager = LinearLayoutManager(activity)
        //rv.adapter = adapter

    }
*/
}