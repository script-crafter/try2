// com/example/try2/MainActivity.kt
package com.example.try2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // Check if user is logged in
        val user = auth.currentUser
        if (user == null) {
            // Not signed in, redirect to the NEW SignUp activity
            startActivity(Intent(this, SignUpActivity::class.java))
            finish() // Call finish to remove this activity from the back stack
            return // Stop further execution in onCreate
        } else {
            // User is signed in, display a welcome message
            welcomeTextView.text = "Welcome, ${user.email}"
        }

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}