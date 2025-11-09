package com.dwlhm.onboarding.ui

import com.dwlhm.nav.Routes
import com.dwlhm.nav.aliases.RouteContent
import com.dwlhm.nav.aliases.RouteRegistrar

fun registerOnboarding(registrar: RouteRegistrar, content: RouteContent) {
    registrar.register(Routes.ONBOARDING, content)
}
