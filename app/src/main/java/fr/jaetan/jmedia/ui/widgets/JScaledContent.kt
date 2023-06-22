package fr.jaetan.jmedia.ui.widgets

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun JScaledContent(
    modifier: Modifier = Modifier,
    onPressed: () -> Unit,
    onLongPressed: () -> Unit = {},
    pressedScale: Float = .8f,
    content: @Composable BoxScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) pressedScale else 1f, label = "")

    Box(
        modifier = modifier
            .scale(scale)
            .pointerInput(listOf(PointerEventType.Exit, PointerEventType.Enter, PointerEventType.Press)) {
                detectTapGestures(
                    onPress = {
                        isPressed = true

                        if (tryAwaitRelease() && isPressed) {
                            onPressed()
                        }

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