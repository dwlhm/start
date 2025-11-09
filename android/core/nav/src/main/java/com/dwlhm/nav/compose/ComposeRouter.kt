package com.dwlhm.nav.compose

import androidx.navigation.NavHostController
import com.dwlhm.nav.aliases.Router

class ComposeRouter(private val nav: NavHostController) : Router {
    override fun navigate(route: String, popUpFrom: String?, inclusive: Boolean) {
        nav.navigate(route) {
            if (popUpFrom != null) {
                popUpTo(popUpFrom) { this.inclusive = inclusive }
            }
        }
    }
}