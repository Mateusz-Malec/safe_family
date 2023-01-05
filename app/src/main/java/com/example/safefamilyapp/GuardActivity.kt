package com.example.safefamilyapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.widget.Toast
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
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class GuardActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fab: FloatingActionButton
    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2
    private lateinit var list: List<Location>
    lateinit var latLng: LatLng

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guard)

        fab = findViewById(R.id.fabSOS)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        /*fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
            if (location == null)
                    Toast.makeText(this, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    list = listOf(location)
                }
                // Got last known location. In some rare situations this can be null.
//                val geocoder = Geocoder(this, Locale.getDefault())
//                list =
//                    geocoder.getFromLocation(location!!.latitude, location.longitude, 1)
            }*/

        fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(this, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    list = listOf(location)
                    //latLng = LatLng(list[0].latitude, list[0].longitude)

                    //mMap.addMarker(MarkerOptions().position(p1))
                }

            }

        fab.setOnClickListener {
            /*Snackbar.make(it,
                list[0].postalCode + " " + list[0].featureName + " "
                        + list[0].subAdminArea,
                Snackbar.LENGTH_LONG).show()*/

            val p1 = LatLng(list[0].latitude, list[0].longitude)
            Toast.makeText(this,
                p1.toString(),
                Toast.LENGTH_LONG).show()
            //mMap.addMarker(MarkerOptions()
             //   .position(p1))
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            // in the below line, we are checking if permission is granted.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if permissions are granted we are displaying below toast message.
                Toast.makeText(applicationContext, "Permission granted.", Toast.LENGTH_SHORT).show()
            } else {
                // in the below line, we are displaying toast message if permissions are not granted.
                Toast.makeText(applicationContext, "Permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}