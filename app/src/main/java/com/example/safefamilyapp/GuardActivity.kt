package com.example.safefamilyapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class GuardActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fab: FloatingActionButton
    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2
    private lateinit var list: List<Address>

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guard)

        fab = findViewById(R.id.fabSOS)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode)
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                val geocoder = Geocoder(this, Locale.getDefault())
                list =
                    geocoder.getFromLocation(location!!.latitude, location!!.longitude, 1)
            }

        fab.setOnClickListener {
            Snackbar.make(it,
                list[0].postalCode + " " + list[0].featureName + " "
                        + list[0].subAdminArea,
                Snackbar.LENGTH_LONG).show()
            //Toast.makeText(this, list[0].latitude.toString() + list[0].longitude.toString(), Toast.LENGTH_LONG).show()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.guardMapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Współrzędne miejsca
        val p = LatLng(50.81, 19.1)

        mMap.addMarker(MarkerOptions()
            .position(p)
            //.title(getAddress(place.latitude, place.longitude))
            //.title("(${place.latitude}, ${place.longitude})")
            .title("Jesteś tutaj")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,10f))
        //mMap.animateCamera(CameraUpdateFactory.zoomIn())

        val cameraPosition = CameraPosition.Builder()
            .target(p) // Sets the center of the map to Mountain View
            .zoom(17f)            // Sets the zoom
            //.bearing(90f)         // Sets the orientation of the camera to east
            //.tilt(30f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

}