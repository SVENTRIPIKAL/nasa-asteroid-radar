// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()  // timberLog, kotlinx-serializer
    }

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")   // safeArgs
    }
}

plugins {
    id("com.android.application") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"    // kotlinx-serializer
}

// NOTE: Do not place your application dependencies here; they belong
// in the individual module build.gradle files
