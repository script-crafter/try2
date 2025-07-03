package com.example.dhakaparkdriver.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.dhakaparkdriver.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText

class ProfileSettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the toolbar for navigation
        setupToolbar(view)

        // Load user data into fields
        loadUserProfile(view)

        // Setup click listeners for all buttons
        setupClickListeners(view)
    }

    private fun setupToolbar(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.profile_toolbar)
        // This will handle the back arrow click automatically
        NavigationUI.setupWithNavController(toolbar, findNavController())
    }

    private fun loadUserProfile(view: View) {
        // Find views
        val profileImage = view.findViewById<ShapeableImageView>(R.id.profile_image)
        val fullNameEditText = view.findViewById<TextInputEditText>(R.id.full_name_edit_text)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.email_edit_text)
        val phoneEditText = view.findViewById<TextInputEditText>(R.id.phone_edit_text)

        // TODO: Replace this with actual data from your ViewModel or Firestore
        // For example: val user = viewModel.currentUser.value
        val mockFullName = "John Doe"
        val mockEmail = "john.doe@example.com"
        val mockPhone = "+1234567890"

        // Set the data into the views
        fullNameEditText.setText(mockFullName)
        emailEditText.setText(mockEmail)
        phoneEditText.setText(mockPhone)

        // TODO: Use a library like Glide or Coil to load the profile picture
        // For example:
        // Glide.with(this)
        //      .load(user.photoUrl)
        //      .placeholder(R.drawable.ic_profile_placeholder)
        //      .into(profileImage)
    }

    private fun setupClickListeners(view: View) {
        val changePhotoButton = view.findViewById<MaterialButton>(R.id.change_photo_button)
        val changePasswordButton = view.findViewById<MaterialButton>(R.id.change_password_button)
        val saveProfileButton = view.findViewById<MaterialButton>(R.id.save_profile_button)

        changePhotoButton.setOnClickListener {
            // TODO: Launch an image picker to select a new photo.
            // Use the Activity Result API for this.
            Toast.makeText(context, "Image picker should launch", Toast.LENGTH_SHORT).show()
        }

        changePasswordButton.setOnClickListener {
            // TODO: Create a new fragment for changing password and add an action in nav_graph.xml
            // val action = ProfileSettingsFragmentDirections.actionProfileSettingsFragmentToChangePasswordFragment()
            // findNavController().navigate(action)
            Toast.makeText(context, "Navigate to Change Password screen", Toast.LENGTH_SHORT).show()
        }

        saveProfileButton.setOnClickListener {
            saveChanges(view)
        }
    }

    private fun saveChanges(view: View) {
        // Get current values from EditText fields
        val fullName = view.findViewById<TextInputEditText>(R.id.full_name_edit_text).text.toString().trim()
        val phone = view.findViewById<TextInputEditText>(R.id.phone_edit_text).text.toString().trim()

        // Simple validation
        if (fullName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Send this data to your ViewModel to save it to Firestore or your backend.
        // For example:
        // viewModel.updateUserProfile(fullName, phone)
        //
        // You would observe the result in the fragment and show success/error messages.

        Toast.makeText(context, "Changes Saved!", Toast.LENGTH_LONG).show()

        // Optionally, navigate back after saving
        findNavController().popBackStack()
    }
}