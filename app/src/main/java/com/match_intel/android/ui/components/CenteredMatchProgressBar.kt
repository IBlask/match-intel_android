package com.match_intel.android.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CenteredMatchProgressBar(
    scoreA: Int,
    scoreB: Int
) {
    val modifier = Modifier
        .fillMaxWidth()
        .height(24.dp)

    var startA by remember { mutableFloatStateOf(0f) }
    var startB by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(scoreA, scoreB) {
        startA = scoreA.toFloat() / 100
        startB = scoreB.toFloat() / 100
    }

    val animatedPercentA by animateFloatAsState(
        targetValue = startA,
        animationSpec = tween(1200, easing = LinearOutSlowInEasing)
    )
    val animatedPercentB by animateFloatAsState(
        targetValue = startB,
        animationSpec = tween(1200, easing = LinearOutSlowInEasing)
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        val halfWidth = maxWidth / 2

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = halfWidth - (halfWidth * animatedPercentA))
                .width(halfWidth * animatedPercentA)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                .background(Color(0xFF2196F3))
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = halfWidth)
                .width(halfWidth * animatedPercentB)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp))
                .background(Color(0xFFF44336))
        )

        Text(
            text = "${(animatedPercentA * 100).toInt()}%   ${(animatedPercentB * 100).toInt()}%",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}
