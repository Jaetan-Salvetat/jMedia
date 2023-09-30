package fr.jaetan.jmedia.ui.shared

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.ui.theme.JColor
import fr.jaetan.jmedia.ui.widgets.JBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorSheet(@StringRes message: Int, isVisible: Boolean, dismiss: () -> Unit) {
    JBottomSheet(isVisible = isVisible, dismiss = dismiss) {
        val state = remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visibleState = state,
                enter = scaleIn(
                    animationSpec = spring(.15f, Spring.StiffnessMediumLow),
                    initialScale = .5f
                ),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = null,
                    tint = JColor.Red,
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .size(80.dp)
                )
            }
            Text(
                text = stringResource(message),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
        }
    }
}