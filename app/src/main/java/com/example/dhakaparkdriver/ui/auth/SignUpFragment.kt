package com.example.dhakaparkdriver.ui.auth

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dhakaparkdriver.R
import com.google.android.material.checkbox.MaterialCheckBox

class SignUpFragment : Fragment() {

    // Using the 'by viewModels()' delegate to get the ViewModel instance
    private val viewModel: SignUpViewModel by viewModels()
    private var documentUri: Uri? = null

    // Views
    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var parkingSpotsEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var documentNameTextView: TextView
    private lateinit var termsCheckbox: MaterialCheckBox
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar

    // Activity Result Launcher for picking a document
    private val documentPickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            documentUri = it
            // You can try to get the real file name, but for now, this is safe
            documentNameTextView.text = "File selected"
            Toast.makeText(requireContext(), "Document selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)
        setupClickListeners(view)
        observeViewModel()
    }

    private fun bindViews(view: View) {
        fullNameEditText = view.findViewById(R.id.fullNameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        phoneEditText = view.findViewById(R.id.phoneEditText)
        addressEditText = view.findViewById(R.id.addressEditText)
        parkingSpotsEditText = view.findViewById(R.id.parkingSpotsEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        documentNameTextView = view.findViewById(R.id.documentNameTextView)
        termsCheckbox = view.findViewById(R.id.termsCheckbox)
        signUpButton = view.findViewById(R.id.signUpButton)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun setupClickListeners(view: View) {
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val fullName = fullNameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val parkingSpots = parkingSpotsEditText.text.toString().trim()
            val termsAccepted = termsCheckbox.isChecked

            // Hand off the data to the ViewModel to handle the logic
            viewModel.signUpUser(
                email, password, confirmPassword, fullName, phone, address,
                parkingSpots, documentUri, termsAccepted
            )
        }

        view.findViewById<TextView>(R.id.loginLink).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<Button>(R.id.uploadDocumentButton).setOnClickListener {
            // Launch the document picker. You can specify MIME types like "image/*" or "application/pdf"
            documentPickerLauncher.launch("*/*")
        }
    }

    private fun observeViewModel() {
        viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is SignUpViewModel.SignUpResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    signUpButton.isEnabled = false
                }
                is SignUpViewModel.SignUpResult.Success -> {
                    progressBar.visibility = View.GONE
                    signUpButton.isEnabled = true
                    Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_dashboardFragment)
                }
                is SignUpViewModel.SignUpResult.Error -> {
                    progressBar.visibility = View.GONE
                    signUpButton.isEnabled = true
                    Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}