object Android {
    object Versions {
        const val compose = "1.3.1"
        const val composeNavigation = "2.5.3"
        const val composePaging = "1.0.0-alpha17"
        const val coreKtx = "1.7.0"
        const val lifecycleRuntime = "2.3.1"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}"

    object Compose {
        const val activity = "androidx.activity:activity-compose:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
        const val paging = "androidx.paging:paging-compose:${Versions.composePaging}"
        const val uiTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    }
}
