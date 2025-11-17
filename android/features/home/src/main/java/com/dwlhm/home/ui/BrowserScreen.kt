package com.dwlhm.home.ui

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.dwlhm.browser.BrowserViewModel
import com.dwlhm.browser.GeckoViewContainer

@Composable
fun BrowserScreen(modifier: Modifier) {
    val context = LocalContext.current
    val viewModel = remember {
        BrowserViewModel(context.applicationContext as Application)
    }
    val activeTab by viewModel.activeTab.collectAsState()

    Column(
        modifier = modifier
    ) {
        GeckoViewContainer(
            tab = activeTab,
            modifier = Modifier.fillMaxSize()
        )
    }
}