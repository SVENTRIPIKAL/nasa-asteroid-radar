plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id ("androidx.navigation.safeargs.kotlin")      // safeArgs
    id ("kotlin-parcelize")                         // parcelize
    id ("com.google.devtools.ksp")                  // ksp for Glide
    id ("kotlin-kapt")                              // kapt for Room
}

android {
    namespace = "com.sventripikal.nasa_asteroid_radar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sventripikal.nasa_asteroid_radar"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // bindings
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // ViewModel    [persistence object of config changes]
    val viewModelVersion = "2.6.1"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")

    // RecyclerView [lazy column/row/grid viewLayout]
    val recyclerViewVersion = "1.3.1"
    implementation("androidx.recyclerview:recyclerview:$recyclerViewVersion")

    // SafeArgs [navigation component]
    val navigationVersion = "2.6.0"
    implementation ("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    androidTestImplementation ("androidx.navigation:navigation-testing:$navigationVersion")

    // Material Design  [UI layouts]
    val materialDesignVersion = "1.9.0"
    implementation ("com.google.android.material:material:$materialDesignVersion")

    // Timber log   [standard logging]
    val timberLogVersion = "5.0.1"
    implementation ("com.jakewharton.timber:timber:$timberLogVersion")

    // Moshi    [JSON serializer]   <Same as KotlinX-Serializer>
    val moshiVersion = "1.14.0"
    implementation ("com.squareup.moshi:moshi:$moshiVersion")
    val moshiConverterVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:converter-moshi:$moshiConverterVersion")

    // Glide    [async image loader]    <Same as COIL>
    val glideVersion = "4.9.0"
    implementation ("com.github.bumptech.glide:glide:$glideVersion")

    // Retrofit [network requests]
    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")

    // Room database    [offline data caching]
    val roomDatabaseVersion = "2.5.2"
    implementation ("androidx.room:room-runtime:$roomDatabaseVersion")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:$roomDatabaseVersion")

    // Coroutines   [asynchronous threading]
    val coroutinesVersion = "1.6.4"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Work Manager [resource usage manager]
    val workManagerVersion = "2.8.1"
    implementation ("androidx.work:work-runtime-ktx:$workManagerVersion")



    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}