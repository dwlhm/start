package com.dwlhm.nav.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwlhm.nav.aliases.RouteContent
import com.dwlhm.nav.aliases.RouteRegistrar

class ComposeRegistrar(private val builder : NavGraphBuilder) : RouteRegistrar {
    override fun register(
        route: String,
        content: RouteContent
    ) {
        builder.composable(
            route = route,
        ) { content() }
    }
}