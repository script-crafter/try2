// com/example/try2/SignUpActivity.kt
package com.example.try2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        // Note: The UI in the image doesn't show a password field,
        // but it's needed for standard email/password sign-up.
        // We can hide it and show it on the next step, or just keep it simple here.
        // For now, let's assume the "Continue" button is for email sign-up.
        // We'll create a dummy password to create the user for this example.
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginLink = findViewById<TextView>(R.id.loginLink)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()

            // For a real app, you would prompt for a password here.
            // For this example, we'll use a hardcoded password.
            // In your real app, you should add a password EditText to your layout.
            val password = "dummypassword123"

            if (email.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI and navigate to MainActivity
                            Log.d("SignUpActivity", "createUserWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(baseContext, "Account created successfully.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
            }
        }

        loginLink.setOnClickListener {
            // For now, we assume you have a LoginActivity.
            // If not, you can create a simple one.
            Toast.makeText(this, "Redirecting to Login...", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already signed in, go to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}