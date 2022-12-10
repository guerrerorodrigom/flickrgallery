object Dependencies {
    object Versions {
        const val coil = "2.2.2"
        const val espresso = "3.5.0"
        const val gradleAndroid = "7.3.1"
        const val gradleKotlinPlugin = "1.7.10"
        const val hilt = "2.44.2"
        const val hiltNavigation = "1.0.0"
        const val junit = "4.13.2"
        const val junitExt = "1.1.4"
        const val lottie = "5.2.0"
        const val retrofit = "2.9.0"
    }

    const val androidAppGradle = "com.android.application"
    const val androidLibraryGradle = "com.android.library"
    const val kotlinGradle = "org.jetbrains.kotlin.android"
    const val hiltGradle = "com.google.dagger.hilt.android"

    object Coil {
        const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
        const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Lottie {
        const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Testing {
        const val  junit = "junit:junit:${Versions.junit}"
        const val  junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
        const val  espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val  composeJunit = "androidx.compose.ui:ui-test-junit4:${Android.Versions.compose}"
        const val  composeUiTooling = "androidx.compose.ui:ui-tooling:${Android.Versions.compose}"
        const val  composeUiManifest = "androidx.compose.ui:ui-test-manifest:${Android.Versions.compose}"
    }
}
