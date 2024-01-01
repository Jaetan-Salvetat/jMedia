package fr.jaetan.jmedia.extensions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
fun Modifier.scrollableTopAppBarBackground(state: TopAppBarState): Modifier = composed {
    val color by animateColorAsState(
        targetValue = if (state.overlappedFraction > .01) {
            MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        } else {
            MaterialTheme.colorScheme.background
        },
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = ""
    )


    background(color)
}