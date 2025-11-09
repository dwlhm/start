package com.dwlhm.home.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeRoute() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Text(
        text = "Hello to Home, Screen!",
    )
}