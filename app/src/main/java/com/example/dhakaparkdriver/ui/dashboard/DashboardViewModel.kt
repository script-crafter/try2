package com.example.dhakaparkdriver.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dhakaparkdriver.data.KpiCard
import com.example.dhakaparkdriver.data.RecentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashboardViewModel : ViewModel() {

    // LiveData for the welcome banner
    private val _ownerName = MutableLiveData<String>()
    val ownerName: LiveData<String> = _ownerName

    private val _approvalStatus = MutableLiveData<String>()
    val approvalStatus: LiveData<String> = _approvalStatus

    // LiveData for KPI cards
    private val _kpiCards = MutableLiveData<List<KpiCard>>()
    val kpiCards: LiveData<List<KpiCard>> = _kpiCards

    // LiveData for Recent Activity
    private val _recentActivity = MutableLiveData<List<RecentActivity>>()
    val recentActivity: LiveData<List<RecentActivity>> = _recentActivity


    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        val user = Firebase.auth.currentUser

        // --- Mock Data Population ---
        // TODO: Replace this with real data fetches from Firestore

        // Welcome Banner Data
        _ownerName.value = user?.displayName ?: "Owner" // Or fetch from your "owners" collection
        _approvalStatus.value = "Pending Approval"

        // KPI Cards Data
        _kpiCards.value = listOf(
            KpiCard("Today's Earnings", "$150.00"),
            KpiCard("Occupancy Rate", "75%"),
            KpiCard("Pending Bookings", "3"),
            KpiCard("Monthly Earnings", "$3,250.00")
        )

        // Recent Activity Data
        _recentActivity.value = listOf(
            RecentActivity("Booking confirmed for Spot #3", "10:45 AM"),
            RecentActivity("Document 'lease_agreement.pdf' uploaded", "9:12 AM"),
            RecentActivity("Profile details updated", "Yesterday"),
            RecentActivity("Payout of $450 processed", "2 days ago"),
            RecentActivity("New review received: 4 stars", "2 days ago")
        )
    }
}