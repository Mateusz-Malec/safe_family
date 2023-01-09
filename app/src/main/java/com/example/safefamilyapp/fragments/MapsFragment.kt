package com.example.safefamilyapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.safefamilyapp.GuardsAdapter
import com.example.safefamilyapp.R
import com.example.safefamilyapp.RestApiService
import com.example.safefamilyapp.models.Device
import com.example.safefamilyapp.models.DeviceView
import com.example.safefamilyapp.models.GuardView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val list = ArrayList<Device>()
    private var listOfLocations = ArrayList<LatLng>()

    lateinit var preferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        preferences = requireActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN", null)
        if (token != null) {
            getAll(token)
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return rootView
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Współrzędne miejsca
        //val place = LatLng(50.81, 19.1)

     /*   val place = listOfLocations.first()
        val place2 = listOfLocations.last()

            mMap.addMarker(MarkerOptions()
                .position(place)
            )
        mMap.addMarker(MarkerOptions()
            .position(place2)
        )*/

        val token = preferences.getString("TOKEN", null)
        if (token != null) {
            getAll(token)
        }

        for (place in list){
            mMap.addMarker(MarkerOptions()
                .position(LatLng(place.Latitude, place.Longtitute))
                .title(place.DeviceDescription)
            )

            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(place.Latitude, place.Longtitute)) // Sets the center of the map to Mountain View
                .zoom(10f)            // Sets the zoom
                //.bearing(90f)         // Sets the orientation of the camera to east
                .tilt(30f)            // Sets the tilt of the camera to 30 degrees
                .build()              // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }



    }

/*// Funkcja, która zamienia współrzędne na adres (ulica, numer, miejscowość, kraj)
private fun getAddress(latLng: LatLng): String {
    val geocoder = Geocoder(requireContext())
    val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    return list?.last()!!.getAddressLine(1)
}*/

/*private fun getGuards(token: String) {
    val apiService = RestApiService()
    apiService.getGuards("Bearer $token") {
        if (it == null) {
            Snackbar.make(requireView(), "Error", Snackbar.LENGTH_LONG).show()
            //Log.e("Register Error", "Błąd")
        } else {
            for (item in it) {
                for (i in item.Devices)
                //list.add(item)
                list.add(i)
                Log.i("Guard item", item.toString())
                //l.add(item)
            }
            Log.i("Lista", list.toString())
        }
    }

}*/

private fun getAll(token: String) {
    val apiService = RestApiService()
    apiService.getAll("Bearer $token") {
        if (it == null) {
            Snackbar.make(requireView(), "Error", Snackbar.LENGTH_LONG).show()
            //Log.e("Register Error", "Błąd")
        } else {
            for (item in it) {
                //list.add(item)
                list.add(item)
                val latLng = LatLng(item.Latitude, item.Longtitute)
                listOfLocations.add(latLng)
                Log.i("Localize item", item.toString())
                //l.add(item)
            }
            Log.i("Localize item", listOfLocations.toString())

        }
    }

    //val adapter = GuardsAdapter(l)
    //rv.layoutManager = LinearLayoutManager(activity)
    //rv.adapter = adapter

}
}