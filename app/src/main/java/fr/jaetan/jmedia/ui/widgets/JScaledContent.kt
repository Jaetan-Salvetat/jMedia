package fr.jaetan.jmedia.ui.widgets

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@SuppressLint("ModifierParameter")
@Composable
fun JScaledContent(modifier: Modifier = Modifier.padding(10.dp),
                   onPressed: () -> Unit,
                   onLongPressed: () -> Unit = {},
                   pressedScale: Float = .7f,
                   content: @Composable BoxScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        if (isPressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    Box(
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onPressed()
                    },
                    onPress = {
                        isPressed = true

                        tryAwaitRelease()

                        isPressed = false
                    },
                    onLongPress = {
                        isPressed = false
                        onLongPressed()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}