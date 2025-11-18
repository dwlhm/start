package com.dwlhm.home.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    progress: Int,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    // Convert progress (0-100) to float (0f-1f)
    val progressFloat = (progress / 100f).coerceIn(0f, 1f)
    
    // Animate progress change
    val animatedProgress by animateFloatAsState(
        targetValue = progressFloat,
        animationSpec = tween(durationMillis = 200),
        label = "loading_progress"
    )
    
    // Animate blinking effect when loading but progress is still 0
    val infiniteTransition = rememberInfiniteTransition(label = "blinking")
    val blinkingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinking_alpha"
    )
    
    // Use blinking alpha when loading and progress is 0, otherwise use normal alpha
    val backgroundAlpha = if (isLoading && progress == 0) blinkingAlpha else 0.3f
    
    // Only show when loading or progress > 0
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(3.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background track (subtle) - kelap kelip ketika loading tapi progress masih 0
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .alpha(backgroundAlpha)
                    .background(Color.White)
            )

            if (isLoading || progress > 0) {
            // Progress bar
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(animatedProgress)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                    )
                }
            }
        }
}

