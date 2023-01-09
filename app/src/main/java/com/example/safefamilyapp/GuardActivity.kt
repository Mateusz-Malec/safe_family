package com.example.safefamilyapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.safefamilyapp.models.Device
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class GuardActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fab: FloatingActionButton
    private lateinit var mMap: GoogleMap

    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS = 20000L
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e., how often you should receive
    // updates, the priority, etc.
    private lateinit var locationRequest: com.google.android.gms.location.LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient has a new Location.
    private lateinit var locationCallback: LocationCallback


    private var currentLocation: Location? = null

    private val locationPermissionCode = 2
    private lateinit var list: List<Location>
    lateinit var latLng: LatLng
    lateinit var preferences: SharedPreferences

    lateinit var profile: TextView

    lateinit var id: String

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission", "HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guard)

        fab = findViewById(R.id.fabSOS)
        profile = findViewById(R.id.GuardProfile)


        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN", null)
        if (token != null) {
            displayProfile(token)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermission()

        locationRequest = com.google.android.gms.location.LocationRequest.create()
            .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)


        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)


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

        /*fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())*/

        /*fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,
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

                }

            }*/

        requestLocationUpdates()


        fab.setOnClickListener {
            /*Snackbar.make(it,
                list[0].postalCode + " " + list[0].featureName + " "
                        + list[0].subAdminArea,
                Snackbar.LENGTH_LONG).show()*/

            /*val p1 = LatLng(list[0].latitude, list[0].longitude)
            Toast.makeText(this,
                p1.toString(),
                Toast.LENGTH_LONG).show()

            val token = preferences.getString("TOKEN", null)
*/
            //val token = intent.getStringExtra("tokenForDevice")
            val intent = Intent(this, AddDeviceActivity::class.java)
            //intent.putExtra("tokenForDevice", token)
            startActivity(intent)

            //val token = intent.getStringExtra("tokenForDevice")
            //val tokenForDevice = Intent(this, GuardActivity::class.java)
            //val tokenForGuard = Intent(this, AddGuardActivity::class.java)
            //tokenForDevice.putExtra("tokenForDevice", it.Token)
            //mMap.addMarker(MarkerOptions()
            //   .position(p1))
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.guardMapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun checkPermission() {
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
    }

    /*private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }*/

    private val mCallBack = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            Log.i("onLocationResult", "${p0.locations}")
            super.onLocationResult(p0)

            currentLocation = p0.lastLocation

            val device = Device(id,
                Build.MANUFACTURER,
                1,
                p0.lastLocation!!.longitude,
                p0.lastLocation!!.latitude)

            /*Toast.makeText(applicationContext,
                //list[0].postalCode + " " + list[0].featureName + " "
                // + list[0].subAdminArea,
                device.toString(),
                Toast.LENGTH_LONG).show()*/
            sendLocation(device)
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            Log.i("onLocationAvailability", "${p0.isLocationAvailable}")
            super.onLocationAvailability(p0)
        }

    }


    @SuppressLint("MissingPermission")
    fun requestLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                mCallBack, Looper.myLooper()
            )
        } catch (ex: SecurityException) {
            Log.e(ContentValues.TAG, "Lost location permission. Could not request updates. $ex")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun sendLocation(device: Device) {
        //val token = intent.getStringExtra("tokenForGuard")

        val token = preferences.getString("TOKEN", null)
        GlobalScope.launch {
            val apiService = RestApiService()
            apiService.sendLocation("Bearer $token", device) {
                if (it == null) {
                    Log.e("Register Guard Error", "Błąd")
                } else {
                    Toast.makeText(this@GuardActivity, "Lokalizowanie", Toast.LENGTH_SHORT).show()
                    //finish()
                }
            }
        }
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

    private fun displayProfile(token: String) {
        val apiService = RestApiService()

        apiService.displayProfile("Bearer $token") {
            if (it == null) {
                //Log.e("Register Error", "Błąd")
            } else {
                profile.text = it.Login
            }
        }
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