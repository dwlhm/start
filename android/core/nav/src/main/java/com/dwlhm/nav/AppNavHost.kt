package com.dwlhm.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dwlhm.nav.aliases.RouteRegistrar
import com.dwlhm.nav.aliases.Router
import com.dwlhm.nav.compose.ComposeRegistrar
import com.dwlhm.nav.compose.ComposeRouter

@Composable
fun AppNavHost(
    startDestination: String,
    registerAll: (registrar: RouteRegistrar, route: Router) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination) {
        val registrar = ComposeRegistrar(this)
        val router = ComposeRouter(navController)
        registerAll(registrar, router)
    }
}