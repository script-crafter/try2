plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    // --- FIXED: Package name now matches the Firebase project ---
    namespace = "com.example.dhakaparkdriver"
    compileSdk = 35

    defaultConfig {
        // --- FIXED: Application ID now matches the Firebase project ---
        applicationId = "com.example.dhakaparkdriver"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    // Added buildFeatures block for viewBinding, assuming it's needed.
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // --- CORRECTED AND ORGANIZED DEPENDENCIES ---

    // Firebase - Bill of Materials (BoM) - Manages versions for all Firebase libs
    implementation(platform(libs.firebase.bom))

    // Firebase Services
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore) // Use the alias if available
    implementation("com.google.firebase:firebase-storage-ktx") // Assuming this isn't in libs.versions.toml yet

    // AndroidX & UI Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3") // Updated to a more recent stable version

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Third-party Libraries
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0") // For charts

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}