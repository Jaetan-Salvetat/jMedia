package fr.jaetan.jmedia.ui.shared

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ProgressView() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")

    val circleColor = Color(0xFFAFE1AF)
    val arcColor = Color(0XFFFFFFFF)

    val arcAngle1 by infiniteTransition.animateFloat(
        label = "arcAngle1",
        initialValue = 0F,
        targetValue = 180F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )

    val arcAngle2 by infiniteTransition.animateFloat(
        label = "arcAngle2",
        initialValue = 180F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val greenCircleAnimation by infiniteTransition.animateFloat(
        label = "green circle animation",
        initialValue = 50f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, delayMillis = 100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .padding(12.dp)
                .size(100.dp)
        ) {
            drawArc(
                color = arcColor,
                startAngle = arcAngle1,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round),
            )

            drawArc(
                color = arcColor,
                startAngle = arcAngle2,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round),
            )

            drawCircle(
                color = arcColor,
                radius = 120f,
            )

            drawCircle(
                color = circleColor,
                radius = greenCircleAnimation,
            )
        }
    }
}