package com.example.safefamilyapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.safefamilyapp.fragments.HomeFragment
import com.example.safefamilyapp.fragments.MapsFragment
import com.example.safefamilyapp.fragments.ProfileFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView
    private val listOfLocations = ArrayList<LatLng>()
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fab = findViewById(R.id.fabAddGuard)

        //val token = intent.getStringExtra("token")

        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN", null)
        if (token != null) {
            getAll(token)
        }
        //val x = Gson().toJson(token)
        //Toast.makeText(this,token, Toast.LENGTH_LONG).show()
        //Snackbar.make(it, x, Snackbar.LENGTH_LONG).show()
        supportActionBar?.hide()

        loadFragment(HomeFragment())

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.map_menu_item -> {
                    loadFragment(MapsFragment())
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        fab.setOnClickListener {
            val intent = Intent(this, AddGuardActivity::class.java)
            //intent.putExtra("tokenForGuard", token)
            startActivity(intent)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentsContainer, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getAll(token: String) {
        val apiService = RestApiService()
        apiService.getAll("Bearer $token") {
            if (it != null) {
                for (item in it) {
                    //list.add(item)
                    val latLng = LatLng(item.Latitude, item.Longtitute)
                    listOfLocations.add(latLng)
                    Log.i("Localize item", item.toString())
                    //l.add(item)
                }
                Log.i("Localize item", listOfLocations.toString())

            }
        }
    }
}