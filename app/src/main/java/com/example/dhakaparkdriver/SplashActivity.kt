// Your package name is correct.
package com.example.dhakaparkdriver

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

// No longer needs to know about Firebase. Its job is much simpler now.
// import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    // The 'auth' variable is no longer needed here.
    // private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // The handler is still used to create a timed delay.
        Handler(Looper.getMainLooper()).postDelayed({

            // THE ONLY ACTION: Start MainActivity.
            // MainActivity's NavHostFragment is now responsible for showing the
            // correct starting screen (LoginFragment in our case).
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Finish the SplashActivity so the user can't press "back" to return to it.
            finish()

        }, 1500) // 1.5 second delay
    }
}