package com.example.dhakaparkdriver.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dhakaparkdriver.R

class UserRoleSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_role_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonOwner = view.findViewById<Button>(R.id.button_owner)
        val buttonDriver = view.findViewById<Button>(R.id.button_driver)
        val buttonParkingAttendant = view.findViewById<Button>(R.id.button_parking_attendant)

        // Navigate to Login/SignUp flow for Owner
        buttonOwner.setOnClickListener {
            // This action ID will be created in the next step in nav_graph.xml
            findNavController().navigate(R.id.action_userRoleSelectionFragment_to_loginFragment)
        }

        // Placeholder for Driver flow
        buttonDriver.setOnClickListener {
            Toast.makeText(context, "Driver flow is under development.", Toast.LENGTH_SHORT).show()
        }

        // Placeholder for Parking Attendant flow
        buttonParkingAttendant.setOnClickListener {
            Toast.makeText(context, "Parking Attendant flow is under development.", Toast.LENGTH_SHORT).show()
        }
    }
}