plugins {
  id("com.android.application")
  id("dev.flutter.flutter-gradle-plugin")
}

android {
  namespace = "io.flutter.plugins.flutter_launch_store_example"
  compileSdk = flutter.compileSdkVersion
  ndkVersion = flutter.ndkVersion

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  defaultConfig {
    applicationId = "io.flutter.plugins.flutter_launch_store_example"
    minSdk = flutter.minSdkVersion
    targetSdk = flutter.targetSdkVersion
    versionCode = flutter.versionCode
    versionName = flutter.versionName
  }

  buildTypes {
    getByName("release") {
      signingConfig = signingConfigs.getByName("debug")
    }
  }
}

kotlin {
  compilerOptions {
    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
  }
}

flutter {
  source = "../.."
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.22")
  testImplementation("junit:junit:4.12")
  androidTestImplementation("androidx.test:runner:1.6.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}