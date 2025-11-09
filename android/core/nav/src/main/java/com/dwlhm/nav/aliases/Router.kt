package com.dwlhm.nav.aliases

interface Router {
    fun navigate(route: String, popUpFrom: String?, inclusive: Boolean = false)
}