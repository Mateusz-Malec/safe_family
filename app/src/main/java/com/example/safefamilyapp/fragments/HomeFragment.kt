package com.example.safefamilyapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safefamilyapp.DevicesAdapter
import com.example.safefamilyapp.R
import com.example.safefamilyapp.models.Device

class HomeFragment : Fragment() {

private lateinit var id: String
    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        id = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)


        val list = ArrayList<Device>()

        list.add(Device("urządzenie mamy",id))
        list.add(Device("urządzenie babci", id))

        val recyclerView: RecyclerView = view.findViewById(R.id.device_list)

        val adapter = DevicesAdapter(list)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            // in the below line, we are checking if permission is granted.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if permissions are granted we are displaying below toast message.
                Toast.makeText(context, "Permission granted.", Toast.LENGTH_SHORT).show()
            } else {
                // in the below line, we are displaying toast message if permissions are not granted.
                Toast.makeText(context, "Permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}