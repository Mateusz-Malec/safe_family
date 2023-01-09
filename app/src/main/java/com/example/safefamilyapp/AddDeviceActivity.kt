package com.example.safefamilyapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.safefamilyapp.models.Device
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AddDeviceActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2

    lateinit var devicedesc: TextInputEditText
    lateinit var btn: Button
    lateinit var id: String

    //private val list =  ArrayList<Location>()
    lateinit var preferences: SharedPreferences

    private lateinit var list: List<Location>

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("HardwareIds", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        supportActionBar?.title = "Verify your device"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        btn = findViewById(R.id.btnAddDevice)
        devicedesc = findViewById(R.id.deviceDesc)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE)


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
        /* fusedLocationClient.lastLocation
             .addOnSuccessListener { location: Location? ->
                 list.add(location!!)
             }*/

        btn.setOnClickListener {
            val device = Device(id, devicedesc.text.toString(), 1, list[0].longitude, list[0].latitude)

            //Toast.make(it, device.toString(), Snackbar.LENGTH_LONG).show()
            Toast.makeText(this, device.toString(), Toast.LENGTH_LONG).show()
            Log.i("Device", device.toString())
            addDevice(device)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addDevice(device: Device) {
        //val token = intent.getStringExtra("tokenForUser")
        //val token = intent.getStringExtra("tokenForDevice")

        val token = preferences.getString("TOKEN", null)
        val role = preferences.getString("ROLE", null)
        GlobalScope.launch {
            val apiService = RestApiService()
            if (role == "User") {
                apiService.addDevice("Bearer $token", device) {
                    if (it == null) {
                        Log.e("Error adding device", "Błąd dodawania urządzenia")
                    } else {
                        //Toast.makeText(this@AddGuardActivity, "Zarejestrowano", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

            } else if (role == "Guard") {
                apiService.addDeviceGuard("Bearer $token", device) {
                    if (it == null) {
                        Log.e("Error adding device", "Błąd dodawania urządzenia")
                    } else {
                        //Toast.makeText(this@AddGuardActivity, "Zarejestrowano", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}