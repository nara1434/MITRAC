plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 21
        targetSdk = 33
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
}

dependencies {


    implementation("com.google.android.material:material:1.11.0-alpha03")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")// Use the latest version.
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.squareup.retrofit2:converter-gson")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.camera:camera-camera2:1.1.0-alpha02")
    implementation ("com.google.code.gson:gson:2.8.8")// Use the latest version
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("androidx.core:core-ktx:1.6.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.github.wseemann:FFmpegMediaMetadataRetriever:1.0.14")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation ("com.android.volley:volley:1.2.1")
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("androidx.navigation:navigation-ui:2.7.4")
    testImplementation("junit:junit:4.13.2")

    implementation ("com.google.firebase:firebase-messaging:23.0.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.android.volley:volley:1.2.0")
    implementation ("com.google.firebase:firebase-bom:31.0.0")
    implementation ("com.google.firebase:firebase-datatransport")
    implementation ("com.google.firebase:firebase-messaging:23.3.1")
    implementation ("com.squareup.picasso:picasso:2.71828")







}