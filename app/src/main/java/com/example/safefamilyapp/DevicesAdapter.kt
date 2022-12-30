package com.example.safefamilyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safefamilyapp.models.Device

class DevicesAdapter(val list: List<Device>) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val deviceName: TextView
        val id: TextView

        init {
            deviceName = view.findViewById(R.id.device_name)
            id = view.findViewById(R.id.device_id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.deviceName.text = list[position].name
        holder.id.text = list[position].id
    }
}