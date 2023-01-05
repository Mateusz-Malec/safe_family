package com.example.safefamilyapp.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safefamilyapp.*
import com.example.safefamilyapp.models.Device
import com.example.safefamilyapp.models.GuardView

class HomeFragment : Fragment() {

    private lateinit var id: String

    private val list = ArrayList<GuardView>()

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        id = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)


        //val fullResponse = requireActivity().intent.extras!!.getString("token")
        val fullResponse2 = requireActivity().intent.extras!!.getString("token")

        getGuards(fullResponse2!!)

        //val json = Gson().toJson(fullResponse)
        //list.add(Device(1, "fullResponse!!", 1.1, 1.1))
        //list.add(Device(1, id, 15.2, 13.4))

        val recyclerView: RecyclerView = view.findViewById(R.id.device_list)

        val list2 = ArrayList<GuardView>()
        val l = ArrayList<Device>()
        l.add(Device(1,"d",3.3,5.6))

        list2.add(GuardView("dssd", "dsd","dssd","sdsd",l))
        val adapter = GuardsAdapter(list)


        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        return view
    }

    private fun getGuards(token: String) {
        val apiService = RestApiService()
        apiService.getGuards("Bearer $token") {
            if (it == null) {
                //Log.e("Register Error", "Błąd")
            } else {
                for (item in it) {
                    list.add(item)
                    Log.i("Guard item", item.toString())
                }
                //Log.i("ProfileInfi", it.toString())

            }
        }
    }

    @Deprecated("Deprecated in Java")
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
                Toast.makeText(context, "Permission granted.", Toast.LENGTH_SHORT).show()
            } else {
                // in the below line, we are displaying toast message if permissions are not granted.
                Toast.makeText(context, "Permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}