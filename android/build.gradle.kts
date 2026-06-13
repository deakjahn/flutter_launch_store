group = "io.flutter.plugins.flutter_launch_store"
version = "1.0-SNAPSHOT"

plugins {
  id("com.android.library")
}

android {
  namespace = "io.flutter.plugins.flutter_launch_store"
  compileSdk = flutter.compileSdkVersion
  ndkVersion = flutter.ndkVersion

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  defaultConfig {
    minSdk = flutter.minSdkVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  lint {
    disable.add("InvalidPackage")
    checkReleaseBuilds = false
  }
}

kotlin {
  compilerOptions {
    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
  }
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.22")
}