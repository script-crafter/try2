package com.example.dhakaparkdriver.ui.dashboard

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dhakaparkdriver.R
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var recentActivityAdapter: RecentActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // No menu code here anymore. Just inflate the layout.
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // Setup all our UI components
        setupToolbar(view)
        setupRecyclerView(view)
        observeViewModel(view)
        setupActionButtons(view) // <-- We added this call for our new buttons
    }

    // This function will set up the click listeners for our 6 new buttons.
    private fun setupActionButtons(view: View) {
        // Find each button by its ID from the XML
        val profileButton = view.findViewById<MaterialButton>(R.id.profile_settings_button)
        val payoutButton = view.findViewById<MaterialButton>(R.id.payout_method_button)
        val revenueButton = view.findViewById<MaterialButton>(R.id.revenue_button)
        val supportButton = view.findViewById<MaterialButton>(R.id.support_button)
        val removeParkingButton = view.findViewById<MaterialButton>(R.id.remove_parking_button)
        val logoutButton = view.findViewById<MaterialButton>(R.id.logout_button)

        // Set an OnClickListener for each button
        profileButton.setOnClickListener {
            // TODO: Replace with your actual navigation action ID
            findNavController().navigate(R.id.action_dashboardFragment_to_profileSettingsFragment)
        }

        payoutButton.setOnClickListener {
            // TODO: Replace with your actual navigation action ID
            // findNavController().navigate(R.id.action_dashboardFragment_to_payoutMethodFragment)
        }

        revenueButton.setOnClickListener {
            // TODO: Replace with your actual navigation action ID
            // findNavController().navigate(R.id.action_dashboardFragment_to_revenueFragment)
        }

        supportButton.setOnClickListener {
            // TODO: Replace with your actual navigation action ID
            // findNavController().navigate(R.id.action_dashboardFragment_to_supportFragment)
        }

        removeParkingButton.setOnClickListener {
            // TODO: Replace with your actual navigation action ID
            // findNavController().navigate(R.id.action_dashboardFragment_to_removeParkingFragment)
        }

        logoutButton.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_dashboardFragment_to_userRoleSelectionFragment)
        }
    }

    private fun setupToolbar(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val navController = findNavController()
        // This makes sure the back arrow doesn't show on the dashboard
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.dashboardFragment))
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recent_activity_recycler_view)
        recentActivityAdapter = RecentActivityAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recentActivityAdapter
    }

    private fun observeViewModel(view: View) {
        val welcomeTextView = view.findViewById<TextView>(R.id.welcome_text)
        val kycStatusTextView = view.findViewById<TextView>(R.id.kyc_status_text)
        // ... (rest of your observer code is unchanged)
        val earningsValueTextView = view.findViewById<TextView>(R.id.earnings_value)
        val occupancyValueTextView = view.findViewById<TextView>(R.id.occupancy_value)
        val chart = view.findViewById<LineChart>(R.id.earnings_chart)

        viewModel.ownerName.observe(viewLifecycleOwner) { name ->
            welcomeTextView.text = "Welcome, $name!"
        }

        viewModel.kycStatus.observe(viewLifecycleOwner) { status ->
            kycStatusTextView.text = status.displayText
            val background = kycStatusTextView.background as GradientDrawable
            background.setColor(ContextCompat.getColor(requireContext(), status.colorRes))
        }

        viewModel.todaysEarnings.observe(viewLifecycleOwner) { earnings ->
            earningsValueTextView.text = String.format("$%.2f", earnings)
        }

        viewModel.occupancyRate.observe(viewLifecycleOwner) { rate ->
            occupancyValueTextView.text = "$rate%"
        }

        viewModel.recentActivities.observe(viewLifecycleOwner) { activities ->
            recentActivityAdapter.updateData(activities)
        }

        chart.description.isEnabled = false
        chart.legend.isEnabled = false
    }
}