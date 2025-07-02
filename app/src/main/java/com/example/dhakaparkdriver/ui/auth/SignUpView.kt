package com.example.dhakaparkdriver.ui.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore
    private val storage = Firebase.storage

    // Sealed class to represent the different states of the sign-up process
    sealed class SignUpResult {
        object Loading : SignUpResult()
        object Success : SignUpResult()
        data class Error(val message: String) : SignUpResult()
    }

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> = _signUpResult

    fun signUpUser(
        email: String,
        pass: String,
        confirmPass: String,
        fullName: String,
        phone: String,
        address: String,
        parkingSpots: String,
        documentUri: Uri?,
        termsAccepted: Boolean
    ) {
        // --- 1. VALIDATION ---
        if (email.isEmpty() || pass.isEmpty() || fullName.isEmpty() || phone.isEmpty() || address.isEmpty() || parkingSpots.isEmpty()) {
            _signUpResult.value = SignUpResult.Error("Please fill all required fields.")
            return
        }
        if (pass != confirmPass) {
            _signUpResult.value = SignUpResult.Error("Passwords do not match.")
            return
        }
        if (pass.length < 6) {
            _signUpResult.value = SignUpResult.Error("Password must be at least 6 characters long.")
            return
        }
        if (documentUri == null) {
            _signUpResult.value = SignUpResult.Error("Please upload the ownership document.")
            return
        }
        if (!termsAccepted) {
            _signUpResult.value = SignUpResult.Error("You must accept the terms and conditions.")
            return
        }

        // --- 2. START SIGN-UP PROCESS ---
        _signUpResult.value = SignUpResult.Loading

        viewModelScope.launch {
            try {
                // --- Create User in Firebase Auth ---
                val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
                val userId = authResult.user?.uid ?: throw Exception("Failed to create user.")

                // --- Upload Document to Firebase Storage ---
                val storageRef = storage.reference.child("owner_documents/$userId/ownership_document")
                val uploadTask = storageRef.putFile(documentUri).await()
                val documentUrl = uploadTask.storage.downloadUrl.await().toString()

                // --- Save User Profile to Firestore ---
                val userProfile = hashMapOf(
                    "uid" to userId,
                    "fullName" to fullName,
                    "email" to email,
                    "phone" to phone,
                    "address" to address,
                    "parkingSpots" to parkingSpots.toIntOrNull(),
                    "documentUrl" to documentUrl
                    // Add other fields like business name, bank details etc.
                )
                firestore.collection("owners").document(userId).set(userProfile).await()

                // --- 3. REPORT SUCCESS ---
                _signUpResult.value = SignUpResult.Success

            } catch (e: Exception) {
                // --- 4. REPORT FAILURE ---
                _signUpResult.value = SignUpResult.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }
}