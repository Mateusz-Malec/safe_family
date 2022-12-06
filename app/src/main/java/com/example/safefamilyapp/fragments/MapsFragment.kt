package com.example.safefamilyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.safefamilyapp.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return rootView
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Współrzędne miejsca
        val place = LatLng(50.81, 19.1)
        mMap.addMarker(MarkerOptions()
            .position(place)
            .title("BABCIA")
        )

        val cameraPosition = CameraPosition.Builder()
            .target(place) // Sets the center of the map to Mountain View
            .zoom(17f)            // Sets the zoom
            //.bearing(90f)         // Sets the orientation of the camera to east
            //.tilt(30f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

}