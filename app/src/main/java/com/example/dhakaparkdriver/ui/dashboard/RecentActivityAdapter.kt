package com.example.dhakaparkdriver.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dhakaparkdriver.R
import com.example.dhakaparkdriver.data.RecentActivity

class RecentActivityAdapter(private val activities: List<RecentActivity>) :
    RecyclerView.Adapter<RecentActivityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.activity_description)
        val timestamp: TextView = view.findViewById(R.id.activity_timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_activity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activities[position]
        holder.description.text = activity.description
        holder.timestamp.text = activity.timestamp
    }

    override fun getItemCount() = activities.size
}