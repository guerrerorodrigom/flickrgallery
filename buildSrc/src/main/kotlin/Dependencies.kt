object Dependencies {
    object Versions {
        const val accompanist = "0.28.0"
        const val coil = "2.2.2"
        const val coroutinesTest = "1.6.4"
        const val coreTesting = "2.1.0"
        const val espresso = "3.5.0"
        const val gradleAndroid = "7.3.1"
        const val gradleKotlinPlugin = "1.7.10"
        const val hilt = "2.44.2"
        const val hiltCompiler = "1.0.0"
        const val hiltNavigation = "1.0.0"
        const val hiltWork = "1.0.0"
        const val junit = "4.13.2"
        const val junitExt = "1.1.4"
        const val lottie = "5.2.0"
        const val mockk = "1.13.3"
        const val retrofit = "2.9.0"
        const val strikt = "0.34.1"
        const val turbine = "0.12.1"
    }

    const val androidAppGradle = "com.android.application"
    const val androidLibraryGradle = "com.android.library"
    const val kotlinGradle = "org.jetbrains.kotlin.android"
    const val hiltGradle = "com.google.dagger.hilt.android"

    object Accompanist {
        const val permissions = "com.google.accompanist:accompanist-permissions:${Versions.accompanist}"
    }
    object Coil {
        const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
    }

    object Hilt {
        const val compiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val navigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
        const val kapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val work = "androidx.hilt:hilt-work:${Versions.hiltWork}"
    }

    object Lottie {
        const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Testing {
        const val composeJunit = "androidx.compose.ui:ui-test-junit4:${Android.Versions.compose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Android.Versions.compose}"
        const val composeUiManifest = "androidx.compose.ui:ui-test-manifest:${Android.Versions.compose}"
        const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val junit = "junit:junit:${Versions.junit}"
        const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val strikt = "io.strikt:strikt-core:${Versions.strikt}"
        const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    }
}
