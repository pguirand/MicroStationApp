plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // HILT Plugins
    alias(libs.plugins.kotlin.kapt) // Kotlin KAPT
    alias(libs.plugins.hilt.android) // HILT Plugin

//    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
}

android {
    namespace = "com.example.microstationapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.microstationapp"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //Compose Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)
    implementation(libs.ui)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)


    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    //okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    //Gson
    implementation(libs.gson)


    //Coroutines
    implementation(libs.kotlinx.coroutines.android)


    //HILT Imports
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


    //HILTViewModels
    implementation(libs.androidx.hilt.navigation.fragment)
    runtimeOnly(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)


    //Image
    implementation(libs.coil.compose)
    implementation(libs.material)
    implementation(libs.roundedimageview)

    //test coroutine
    testImplementation(libs.kotlinx.coroutines.test)

    //LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.runtime.livedata)

    //ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Testing Imports
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Add MockK for Android Instrumentation Tests (UI Tests)
    androidTestImplementation(libs.mockk.android)

    // Add MockK for Unit Tests
    testImplementation(libs.mockk)


    // Room
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.runtime.android)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)


    // MockWebServer for unit testing API calls
    testImplementation(libs.mockwebserver)

    // Gson for JSON serialization & deserialization in tests
    testImplementation(libs.gson)

    // JUnit for testing
    testImplementation(libs.junit)

    // Coroutines test for handling suspend functions in tests
    testImplementation(libs.kotlinx.coroutines.test.v164)

}