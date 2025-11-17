package com.dwlhm.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UrlInputField(
    modifier: Modifier,
    currentUrl: String? = null,
    onGo: (url: String) -> Unit
) {
    val url = rememberTextFieldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isUserEditing by remember { mutableStateOf(false) }
    var lastSyncedUrl by remember { mutableStateOf<String?>(null) }
    var isUpdatingFromUrl by remember { mutableStateOf(false) }

    // Update text field ketika currentUrl berubah (hanya jika user tidak sedang editing)
    LaunchedEffect(currentUrl) {
        if (!isUserEditing && currentUrl != null) {
            val urlText = url.text.toString()
            // Hanya update jika berbeda dan user tidak sedang mengetik
            if (urlText != currentUrl) {
                isUpdatingFromUrl = true
                url.edit { 
                    replace(0, length, currentUrl)
                }
                lastSyncedUrl = currentUrl
                isUpdatingFromUrl = false
            }
        } else if (currentUrl == null) {
            lastSyncedUrl = null
        }
    }
    
    // Track perubahan text untuk mengetahui apakah user sedang editing
    // Hanya trigger jika perubahan bukan dari programmatic update
    LaunchedEffect(url.text) {
        if (!isUpdatingFromUrl) {
            val currentText = url.text.toString()
            // Jika text berbeda dari lastSyncedUrl, berarti user sedang editing
            if (currentText != lastSyncedUrl) {
                isUserEditing = true
            }
        }
    }

    // Function to handle URL submission and dismiss keyboard
    fun handleSubmit() {
        keyboardController?.hide()
        focusManager.clearFocus()
        isUserEditing = false
        val submittedUrl = url.text.toString()
        lastSyncedUrl = submittedUrl
        onGo(submittedUrl)
    }

    Row {
        BasicTextField(
            state = url,
            modifier = modifier
                .weight(1F)
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    color = Color(0x70333333),
                    shape = RoundedCornerShape(26.dp)
                )
                .onFocusChanged { focusState ->
                    // Reset editing state ketika fokus hilang
                    if (!focusState.isFocused && isUserEditing) {
                        // Jika text sama dengan currentUrl, reset editing state
                        if (currentUrl != null && url.text.toString() == currentUrl) {
                            isUserEditing = false
                            lastSyncedUrl = currentUrl
                        }
                    }
                },

            textStyle = TextStyle(
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
            ),

            cursorBrush = SolidColor(Color.White),

            lineLimits = TextFieldLineLimits.SingleLine,

            decorator = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    innerTextField()

                    if (url.text.isEmpty()) {
                        Text(
                            "where do we go?",
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White.copy(0.6f)
                            )
                        )
                    }
                }
            },

            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onKeyboardAction = { performDefaultAction ->
                handleSubmit()
            }
        )

        Button(
            onClick = { handleSubmit() },
            shape = RoundedCornerShape(26.dp), // border radius
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.8f), // background
                contentColor = Color.Black // warna teks
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp), // padding custom
            modifier = Modifier
                .height(40.dp)
                .padding(start = 10.dp)
        ) {
            Text("GO")
        }

    }
}