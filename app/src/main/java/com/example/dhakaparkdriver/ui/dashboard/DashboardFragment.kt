package com.example.dhakaparkdriver.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dhakaparkdriver.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarAndDrawer(view)
        setupObservers(view)
        setupCharts(view)
    }

    private fun setupToolbarAndDrawer(view: View) {
        drawerLayout = view.findViewById(R.id.drawer_layout)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            activity, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView = view.findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    Firebase.auth.signOut()
                    findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
                }
                // Handle other navigation items...
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun setupObservers(view: View) {
        val welcomeTextView = view.findViewById<TextView>(R.id.welcome_text)
        val statusTextView = view.findViewById<TextView>(R.id.status_text)
        val kpiTitle1 = view.findViewById<TextView>(R.id.kpi_title_1)
        val kpiValue1 = view.findViewById<TextView>(R.id.kpi_value_1)
        val kpiTitle2 = view.findViewById<TextView>(R.id.kpi_title_2)
        val kpiValue2 = view.findViewById<TextView>(R.id.kpi_value_2)

        viewModel.ownerName.observe(viewLifecycleOwner) { name ->
            welcomeTextView.text = "Welcome, $name!"
        }
        viewModel.approvalStatus.observe(viewLifecycleOwner) { status ->
            statusTextView.text = "Status: $status"
        }
        viewModel.kpiCards.observe(viewLifecycleOwner) { cards ->
            if (cards.size >= 2) {
                kpiTitle1.text = cards[0].title
                kpiValue1.text = cards[0].value
                kpiTitle2.text = cards[1].title
                kpiValue2.text = cards[1].value
                // Update other cards...
            }
        }
        viewModel.recentActivity.observe(viewLifecycleOwner) { activities ->
            val recyclerView = view.findViewById<RecyclerView>(R.id.recent_activity_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = RecentActivityAdapter(activities)
        }
    }

    private fun setupCharts(view: View) {
        val earningsChart = view.findViewById<LineChart>(R.id.earnings_chart)
        // TODO: Replace with real data from ViewModel
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 4f))
        entries.add(Entry(1f, 8f))
        entries.add(Entry(2f, 6f))
        entries.add(Entry(3f, 11f))
        entries.add(Entry(4f, 7f))

        val dataSet = LineDataSet(entries, "Weekly Earnings")
        val lineData = LineData(dataSet)
        earningsChart.data = lineData
        earningsChart.invalidate() // refresh
    }
}