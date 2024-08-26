plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.shopbee"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shopbee"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        dataBinding = true
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

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // StickyScrollView
    implementation("com.github.amarjain07:StickyScrollView:1.0.3")

    // Layout Manager Recycler View
    implementation("com.github.DingMouRen:LayoutManagerGroup:1e6f4f96eb")

    // Toggle Button
    implementation("com.github.angads25:toggle:1.1.0")
}