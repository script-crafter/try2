package com.example.dhakaparkdriver.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dhakaparkdriver.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    // 1. Declare a variable for the Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Initialize the FirebaseAuth instance
        auth = Firebase.auth

        // This part is still correct: if the user is already logged in, go to the dashboard.
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            return
        }

        // 3. Get references to your EditText fields
        val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val signUpLink = view.findViewById<TextView>(R.id.signUpLink)

        loginButton.setOnClickListener {
            // 4. Get the email and password text from the EditText fields
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // 5. Add basic validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please enter email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the function here
            }

            // --- THIS IS THE NEW FIREBASE LOGIN LOGIC ---
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("LoginFragment", "signInWithEmail:success")
                        // Navigate to the dashboard on successful login
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("LoginFragment", "signInWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // This part remains the same
        signUpLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
}