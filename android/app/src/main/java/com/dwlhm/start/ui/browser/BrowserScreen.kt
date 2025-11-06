package com.dwlhm.start.ui.browser

import BrowserViewModel
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BrowserScreen() {
    val context = LocalContext.current
    val viewModel = remember { BrowserViewModel(context.applicationContext as Application) }

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .background(Color(0xFF222222))
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            viewModel.tabs.forEachIndexed { i, _ ->
                val isActive = i == viewModel.activeIndex
                Text(
                    text = "Tab ${i + 1}",
                    color = if (isActive) Color.White else Color.Gray,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { viewModel.switchTab(i) }
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "+",
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        viewModel.newTab("https://google.com")
                    }
            )
        }

        GeckoViewContainer(
            tab = viewModel.activeTab,
            modifier = Modifier.fillMaxSize()
        )
    }
}
