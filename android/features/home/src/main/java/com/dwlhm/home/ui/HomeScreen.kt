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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val viewModel = remember {
        BrowserViewModel(context.applicationContext as Application)
    }
    
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomCardContent(viewModel, bottomSheetState)
        },
        sheetPeekHeight = 80.dp,
        sheetContainerColor = Color.Black,
        sheetDragHandle = null,
        containerColor = Color.White
    ) { paddingValues ->
        MainCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            viewModel = viewModel,
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomCardContent(viewModel: BrowserViewModel, sheetState: SheetState) {
    val activeTab by viewModel.activeTab.collectAsState()
    val currentUrl by viewModel.currentUrl.collectAsState()
    val loadingProgress by viewModel.loadingProgress.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    val isExpanded = sheetState.currentValue == SheetValue.Expanded
    val hasLoadedWeb = activeTab != null
    
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

    // Jika web sudah load, tidak pakai rounded corner
    val cornerRadius = if (hasLoadedWeb) 0.dp else 26.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .clip(RoundedCornerShape(cornerRadius, cornerRadius, 0.dp, 0.dp))
            .background(Color.Black)
            .imePadding() // Tambahkan padding untuk keyboard di seluruh Column
    ) {
        LoadingIndicator(
            progress = loadingProgress,
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp, 16.dp, 0.dp)
        )

        // Welcome text when expanded
        if (isExpanded) {
            Text(
                text = "selamat datang di detail",
                fontFamily = InterScript,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp) // Tinggi tetap: 60dp content + 16dp padding bottom
                .padding(10.dp, 0.dp, 10.dp, 16.dp), // Tambahkan padding bottom untuk keyboard
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // URL Input Field
            UrlInputField(
                modifier = Modifier
                    .weight(1F)
                    .height(40.dp),
                currentUrl = currentUrl,
                onGo = { handleOnGo(it) },
                viewModel,
            )
        }
        
        // Spacer untuk memberikan ruang expand (selalu ada agar bisa di-drag ke atas)
        Spacer(modifier = Modifier.height(300.dp))
    }
}