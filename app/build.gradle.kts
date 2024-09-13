import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.shopbee"
    compileSdk = 34
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }
    val apiKey: String = localProperties.getProperty("apiKey") ?: ""
    defaultConfig {
        applicationId = "com.example.shopbee"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "apiKey", "\"$apiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    // OkHttp3
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // RxJava
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")

    // Nav
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")

    // Dagger
    implementation("com.google.dagger:dagger:2.48")
    annotationProcessor("com.google.dagger:dagger-compiler:2.48")

    // Bottom Bar
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    // Circle image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Rating Bar
    implementation("com.github.ome450901:SimpleRatingBar:1.5.1")

    //AvatarView
    implementation("io.getstream:avatarview-coil:1.0.7")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-storage:20.1.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // StickyScrollView
    implementation("com.github.amarjain07:StickyScrollView:1.0.3")

    // Layout Manager Recycler View
    implementation("com.github.DingMouRen:LayoutManagerGroup:1e6f4f96eb")

    // Toggle Button
    implementation("com.github.angads25:toggle:1.1.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database:21.0.0")

    // spark button
    implementation("com.github.varunest:sparkbutton:1.0.6")

    // Flow Layout Manager
    implementation("com.github.simonebortolin:FlowLayoutManager:1.8.0")

    //CheckBox
    implementation("com.github.animsh:AnimatedCheckBox:1.0.0")
    // Show more text
    implementation("com.github.mahimrocky:ShowMoreText:1.0.2")

    //FAB buttom
    implementation("com.google.android.material:material:1.3.0-alpha02")

    //Google Map
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    //Loading Button
    implementation("com.github.yatindeokar:MyLoadingButton:v1.0.1")

    //Alert Dialog
    implementation("com.saadahmedev.popup-dialog:popup-dialog:2.0.0")

    // loading dialog
    implementation("com.github.razaghimahdi:Android-Loading-Dots:1.3.2")

    // Required for one-shot operations (to use `ListenableFuture` from Guava Android)
    implementation("com.google.guava:guava:31.0.1-android")

    // Required for streaming operations (to use `Publisher` from Reactive Streams)
    implementation("org.reactivestreams:reactive-streams:1.0.4")

    // add the dependency for the Google AI client SDK for Android
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
}