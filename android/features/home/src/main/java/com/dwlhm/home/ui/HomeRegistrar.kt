package com.dwlhm.home.ui

import com.dwlhm.nav.Routes
import com.dwlhm.nav.aliases.RouteContent
import com.dwlhm.nav.aliases.RouteRegistrar

fun registerHome(registrar: RouteRegistrar, content: RouteContent) {
    registrar.register(Routes.HOME, content)
}