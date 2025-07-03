package com.example.dhakaparkdriver.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dhakaparkdriver.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check user session after a short delay to show the splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                // User is logged in, navigate to Dashboard
                findNavController().navigate(R.id.action_splashFragment_to_dashboardFragment)
            } else {
                // User is not logged in, navigate to Role Selection
                findNavController().navigate(R.id.action_splashFragment_to_userRoleSelectionFragment)
            }
        }, 1500) // 1.5 second delay
    }
}