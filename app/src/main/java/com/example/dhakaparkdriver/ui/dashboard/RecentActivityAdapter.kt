package com.example.dhakaparkdriver.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dhakaparkdriver.R
import java.text.SimpleDateFormat
import java.util.Locale

class RecentActivityAdapter(private var activities: List<ActivityItem>) : RecyclerView.Adapter<RecentActivityAdapter.ActivityViewHolder>() {

    class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.activity_icon)
        val text: TextView = itemView.findViewById(R.id.activity_text)
        val timestamp: TextView = itemView.findViewById(R.id.activity_timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        holder.text.text = activity.text

        // Simple formatter for the timestamp
        val sdf = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
        holder.timestamp.text = sdf.format(activity.timestamp)

        // Set icon based on activity type
        val iconRes = when (activity.type.uppercase()) {
            "BOOKING" -> R.drawable.ic_launcher_foreground // Replace with actual icons
            "PAYOUT" -> R.drawable.ic_launcher_foreground
            "REVIEW" -> R.drawable.ic_launcher_foreground
            "UPDATE" -> R.drawable.ic_launcher_foreground
            else -> R.drawable.ic_launcher_foreground
        }
        holder.icon.setImageResource(iconRes)
    }

    override fun getItemCount() = activities.size

    fun updateData(newActivities: List<ActivityItem>) {
        this.activities = newActivities
        notifyDataSetChanged() // For simplicity. For better performance use DiffUtil.
    }
}