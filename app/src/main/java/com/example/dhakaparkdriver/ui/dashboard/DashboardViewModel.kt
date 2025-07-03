package com.example.dhakaparkdriver.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Data class to represent a single activity item
data class ActivityItem(
    val type: String,
    val text: String,
    val timestamp: Date
)

// Enum for KYC status to make code cleaner and safer
enum class KycStatus(val displayText: String, val colorRes: Int) {
    PENDING("Pending", android.R.color.darker_gray),
    VERIFIED("Verified", android.R.color.holo_green_dark),
    REJECTED("Rejected", android.R.color.holo_red_dark),
    UNKNOWN("Unknown", android.R.color.black)
}

class DashboardViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _ownerName = MutableLiveData<String>()
    val ownerName: LiveData<String> = _ownerName

    private val _kycStatus = MutableLiveData<KycStatus>()
    val kycStatus: LiveData<KycStatus> = _kycStatus

    private val _todaysEarnings = MutableLiveData<Double>()
    val todaysEarnings: LiveData<Double> = _todaysEarnings

    private val _occupancyRate = MutableLiveData<Int>()
    val occupancyRate: LiveData<Int> = _occupancyRate

    private val _recentActivities = MutableLiveData<List<ActivityItem>>()
    val recentActivities: LiveData<List<ActivityItem>> = _recentActivities

    // Add LiveData for chart data later if needed, for now we can fetch directly

    init {
        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        val userId = auth.currentUser?.uid ?: return

        // Fetch User Info (Name, KYC)
        db.collection("owners").document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                _ownerName.value = snapshot.getString("name") ?: "Owner"
                val statusString = snapshot.getString("kycStatus") ?: "UNKNOWN"
                _kycStatus.value = KycStatus.valueOf(statusString.uppercase())
            }

        // Fetch Today's Metrics (Earnings, Occupancy)
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        db.collection("owners").document(userId).collection("dailyMetrics").document(todayDate)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    _todaysEarnings.value = 0.0
                    _occupancyRate.value = 0
                    return@addSnapshotListener
                }
                _todaysEarnings.value = snapshot.getDouble("earnings") ?: 0.0
                _occupancyRate.value = snapshot.getLong("occupancyRate")?.toInt() ?: 0
            }

        // Fetch Recent Activities
        db.collection("owners").document(userId).collection("recentActivities")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) return@addSnapshotListener

                val activities = snapshots.mapNotNull { doc ->
                    doc.toObject(ActivityItem::class.java)
                }
                _recentActivities.value = activities
            }
    }
}