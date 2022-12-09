plugins {
    id(Dependencies.androidAppGradle) version Dependencies.Versions.gradleAndroid apply false
    id(Dependencies.androidLibraryGradle) version Dependencies.Versions.gradleAndroid apply false
    id(Dependencies.kotlinGradle) version Dependencies.Versions.gradleKotlinPlugin apply false
    id(Dependencies.hiltGradle) version Dependencies.Versions.hilt apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
