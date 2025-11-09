package com.dwlhm.nav.aliases

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

typealias RouteContent = @Composable () -> Unit

interface RouteRegistrar {
    fun register(
        route: String,
        content: RouteContent
    )
}