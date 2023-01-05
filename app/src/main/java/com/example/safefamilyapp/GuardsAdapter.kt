package com.example.safefamilyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safefamilyapp.models.Device
import com.example.safefamilyapp.models.GuardView

class GuardsAdapter(private val list: List<GuardView>) : RecyclerView.Adapter<GuardsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val guardUsername: TextView
        val guardName: TextView
        val guardLastActive: TextView

        init {
            guardUsername = view.findViewById(R.id.guardUsername)
            guardName = view.findViewById(R.id.guardName)
            guardLastActive = view.findViewById(R.id.guardLastActive)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.guard_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.guardUsername.text = list[position].Login
        holder.guardName.text = "${list[position].Name} ${list[position].SurName}"
        holder.guardLastActive.text = list[position].LastActive
    }
}