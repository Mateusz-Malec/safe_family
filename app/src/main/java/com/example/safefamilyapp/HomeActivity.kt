package com.example.safefamilyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.safefamilyapp.fragments.HomeFragment
import com.example.safefamilyapp.fragments.MapsFragment
import com.example.safefamilyapp.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    lateinit var fab: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fab = findViewById(R.id.fabAddGuard)

        val token = intent.getStringExtra("token")

        val x = Gson().toJson(token)
        Toast.makeText(this,x, Toast.LENGTH_LONG).show()
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

        fab.setOnClickListener{
           val intent = Intent (this, AddGuardActivity::class.java)
                intent.putExtra("tokenForGuard", token)
                startActivity(intent)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentsContainer, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }
}