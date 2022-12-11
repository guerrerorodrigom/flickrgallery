plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.rodrigoguerrero.flickrgallery"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.rodrigoguerrero.flickrgallery"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Android.Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Android.coreKtx)
    implementation(Android.lifecycleRuntime)
    implementation(Android.Compose.activity)
    implementation(Android.Compose.ui)
    implementation(Android.Compose.uiTooling)
    implementation(Android.Compose.material)
    implementation(Android.Compose.foundation)
    implementation(Android.Compose.navigation)
    implementation(Android.Compose.paging)
    implementation(Android.Compose.materialIconsExtended)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gson)

    implementation(Dependencies.Coil.coil)

    implementation(Dependencies.Hilt.hilt)
    implementation(Dependencies.Hilt.navigation)
    implementation(Dependencies.Hilt.work)
    kapt(Dependencies.Hilt.kapt)
    kapt(Dependencies.Hilt.compiler)

    implementation(Android.Jetpack.room)
    implementation(Android.Jetpack.roomKtx)
    kapt(Android.Jetpack.roomKapt)

    implementation(Dependencies.Lottie.lottie)

    implementation(Android.Jetpack.workKtx)

    implementation(Dependencies.Accompanist.permissions)

    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.junitExt)
    androidTestImplementation(Dependencies.Testing.espressoCore)
    androidTestImplementation(Dependencies.Testing.composeJunit)
    debugImplementation(Dependencies.Testing.composeUiTooling)
    debugImplementation(Dependencies.Testing.composeUiManifest)
}

kapt {
    correctErrorTypes = true
}
