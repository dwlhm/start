package com.dwlhm.home.ui

import android.app.Application
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwlhm.browser.BrowserViewModel
import com.dwlhm.browser.GeckoViewContainer
import com.dwlhm.ui.navigation.theme.EuphoriaScript
import com.dwlhm.ui.navigation.theme.InterScript

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val viewModel = remember {
        BrowserViewModel(context.applicationContext as Application)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
//            .padding(26.dp, 26.dp, 26.dp, 10.dp)
    ) {
//        TopCard()
        MainCard(modifier = Modifier
            .weight(1F)
            .fillMaxWidth(),
            viewModel = viewModel,
        )
        BottomCard(viewModel)
    }
}

@Composable
fun TopCard() {
    val isDark = isSystemInDarkTheme()
    val brightness = if (isDark) 0.4f else 0.8f

    val backgroundColor = Color(0xFFFDFDFD).copy(alpha = brightness - 0.02f)

    Column(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Let's",
            fontFamily = EuphoriaScript,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        Row {

            Text(
                text = "Start",
                fontFamily = InterScript,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
            )
            Text(
                text = " the journey",
                fontFamily = InterScript,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun MainCard(modifier: Modifier, viewModel: BrowserViewModel) {
    val activeTab by viewModel.activeTab.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()
    
    // Handle native Android back button
    BackHandler(enabled = canGoBack) {
        viewModel.goBack()
    }
    
    Column(modifier) {
        GeckoViewContainer(
            tab = activeTab,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BottomCard(viewModel: BrowserViewModel) {
    val activeTab by viewModel.activeTab.collectAsState()
    val currentUrl by viewModel.currentUrl.collectAsState()
    
    fun handleOnGo(url: String) {
        val formattedUrl = UrlUtils.formatUrl(url)
        if (formattedUrl.isEmpty()) return
        
        Log.d("omnibox", "Loading URL: $formattedUrl")
        
        // Jika belum ada tab, buat tab baru
        if (activeTab == null) {
            viewModel.newTab(formattedUrl)
        } else {
            // Gunakan activeIndex yang benar, bukan hardcode 0
            viewModel.changeTabUrl(formattedUrl, viewModel.activeIndex)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // URL Input Field
        UrlInputField(
            modifier = Modifier
                .weight(1F)
                .height(40.dp),
            currentUrl = currentUrl,
            onGo = { handleOnGo(it) }
        )
    }
}