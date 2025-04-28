package com.example.healthguard.presentation.home.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SOSButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition()

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        )
    )

    val clickScale = remember { androidx.compose.animation.core.Animatable(1f) }
    var isFlashing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val combinedScale = pulseScale * clickScale.value

    val buttonColor = if (isFlashing) Color.White else MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(200.dp)
            .height(200.dp)
            .scale(combinedScale)
            .background(buttonColor, CircleShape)
            .clickable {
                coroutineScope.launch {
                    isFlashing = true
                    delay(100)
                    isFlashing = false
                }
                coroutineScope.launch {
                    clickScale.animateTo(
                        targetValue = 1.3f,
                        animationSpec = tween(durationMillis = 100)
                    )
                    clickScale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 100)
                    )
                }
                onClick()
            }
    ) {
        Text(
            text = "SOS",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
