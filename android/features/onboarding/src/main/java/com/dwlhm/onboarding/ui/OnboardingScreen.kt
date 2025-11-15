package com.dwlhm.onboarding.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwlhm.ui.navigation.theme.EuphoriaScript
import com.dwlhm.ui.navigation.theme.InterScript
import kotlinx.coroutines.delay

import com.dwlhm.onboarding.ui.SoftGlowParticles
import kotlin.math.max

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    OnboardingContainer(onFinish)
}

@Composable
fun OnboardingContainer(onFinish: () -> Unit) {
    var expanded by remember { mutableStateOf(true) }

//    LaunchedEffect(Unit) {
//        delay(300)
//        expanded = false
//    }

    val transition = updateTransition(targetState = expanded, label = "onboardingCardTransition")
    val scale = transition.animateFloat(
        transitionSpec = { tween(1000, easing = FastOutSlowInEasing) },
        label = "scaleAnim"
    ) { expanded -> if (expanded) 1f else 0.2f }

    val cornerRadius = transition.animateDp(
        transitionSpec = { tween(800, easing = LinearOutSlowInEasing) },
        label = "cornerAnim"
    ) { expanded -> if (expanded) 20.dp else 4.dp }

    val size = transition.animateDp(
        transitionSpec = { tween(900, easing = FastOutSlowInEasing) },
        label = "sizeAnim"
    ) { expanded -> if (expanded) 250.dp else 120.dp }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(26.dp, 26.dp, 26.dp, 10.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(56.dp))
        ) {

            AnimatedSoftWhiteBackground(
                modifier = Modifier.matchParentSize()
            )

            SoftGlowParticles(
                modifier = Modifier
                    .fillMaxSize(),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
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
                AnimatedRotatingText(onFinish)
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedRotatingText(onFinish: () -> Unit) {
    val messages = listOf(
        "Where do we go for today?",
        "Let’s explore something new!",
        "Have a great journey!",
        "97%...",
        "98%...",
        "99%...",
        "🥳"
    )

    var index by remember { mutableStateOf(0) }
    var progress by remember { mutableStateOf(0f) }

    val animationDuration = 3000L

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = animationDuration.toInt(),
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    LaunchedEffect(index) {
        if (index < messages.lastIndex) {
            progress = max(0.1f, index / messages.size.toFloat())
            delay(animationDuration)
            index++
        } else {
            progress = 1f
            delay(animationDuration)
            onFinish()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            targetState = messages[index],
            transitionSpec = {
                fadeIn(tween(600)) togetherWith fadeOut(tween(600))
            }
        ) { text ->
            Text(
                text = text,
                fontFamily = InterScript,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(0.dp,0.dp,0.dp,10.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(3.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black)
            )
        }
    }
}
