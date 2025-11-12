package com.dwlhm.onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwlhm.ui.navigation.theme.EuphoriaScript
import com.dwlhm.ui.navigation.theme.InterScript

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    OnboardingContainer(onFinish = {})
}

@Composable
fun OnboardingContainer(onFinish: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(26.dp, 26.dp, 26.dp, 10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16))
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Let's",
                fontFamily = EuphoriaScript,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            )
            Row {

                Text(
                    text = "Start",
                    fontFamily = InterScript,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = " the journey",
                    fontFamily = InterScript,
                    fontSize = 24.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Black)
                .padding(10.dp), // Sedikit padding vertikal
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(26))
                    .background(Color.White)
            )
        }
    }
}