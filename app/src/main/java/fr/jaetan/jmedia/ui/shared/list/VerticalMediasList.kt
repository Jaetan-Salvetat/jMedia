package fr.jaetan.jmedia.ui.shared.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.locals.LocalMediaManager
import fr.jaetan.jmedia.models.medias.IMedia
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.ui.shared.JTag
import fr.jaetan.jmedia.ui.theme.JColor
import fr.jaetan.jmedia.ui.widgets.JScaledContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun VerticalMediasListItem(media: IMedia, modifier: Modifier, backgroundColor: Color = MaterialTheme.colorScheme.background, isShowingTag: Boolean = true) {
    val scope = rememberCoroutineScope()
    val mediasManager = LocalMediaManager.current
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current
    val actionsButtonsSize = 70.dp

    // States
    var hasVibrate by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val actionButtonColor by animateColorAsState(
        targetValue = if (hasVibrate) {
            JColor.Red
        } else {
            MaterialTheme.colorScheme.scrim
        },
        label = "actionButtonColor"
    )
    val actionButtonScale by animateFloatAsState(
        targetValue = if (hasVibrate) {
            1.2f
        } else {
            1f
        },
        label = "actionButtonScale"
    )
    val state = rememberDraggableState { delta ->
        with(density) {
            val newValue = offsetX.value + delta

            if (newValue.roundToInt().toDp() < -actionsButtonsSize && !hasVibrate) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                hasVibrate = true
            } else if (newValue.roundToInt().toDp() > -actionsButtonsSize) {
                hasVibrate = false
            }
            scope.launch { offsetX.animateTo(newValue, spring(stiffness =  Spring.StiffnessVeryLow, visibilityThreshold = 0f)) }
        }
    }

    // Methods
    val onDragStopped: CoroutineScope.(Float) -> Unit = {
        with(density) {
            if (offsetX.value.roundToInt().toDp() < -actionsButtonsSize) {
                mediasManager.libraryHandler(media)
            }

            scope.launch {
                offsetX.animateTo(0f, spring(stiffness =  Spring.StiffnessMedium))
            }

            hasVibrate = false
        }
    }

    // UI
    Box(
        modifier
            .height(140.dp)
            .background(actionButtonColor)) {
        // Action button
        ActionButton(media, actionsButtonsSize, actionButtonScale, Modifier.align(Alignment.CenterEnd))

        // Media details
        Column {
            Row(
                modifier = Modifier
                    .offset { IntOffset(x = offsetX.value.roundToInt(), y = 0) }
                    .draggable(
                        state = state,
                        orientation = Orientation.Horizontal,
                        onDragStopped = onDragStopped
                    )
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .clickable { }
                    .padding(start = 20.dp)
                    .padding(vertical = 15.dp)
            ) {
                ImageCell(media.image)

                Column(Modifier.padding(horizontal = 20.dp)) {
                    MediaTitleCell(media)
                    TagCell(media.type, isShowingTag)
                    SynopsisCell(media.synopsis)
                }
            }
        }
    }
}

@Composable
private fun ActionButton(media: IMedia, buttonSize: Dp, iconScale: Float, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .width(buttonSize)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = if (media.isInLibrary) {
                    painterResource(R.drawable.heart_minus_24px)
                } else {
                    painterResource(R.drawable.heart_plus_24px)
                },
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.scale(iconScale)
            )
        }
    }
}

@Composable
private fun ImageCell(image: Image?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image?.imageUrl)
            .crossfade(true)
            .error(R.drawable.placeholder)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(70.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    )
}

@Composable
private fun MediaTitleCell(media: IMedia) {
    val mediasManager = LocalMediaManager.current

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = media.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        JScaledContent(
            onPressed = { mediasManager.libraryHandler(media) },
            modifier = Modifier.padding(horizontal = 10.dp)) {
            Icon(
                painter = if (media.isInLibrary) {
                    painterResource(R.drawable.heart_minus_24px)
                } else {
                    painterResource(R.drawable.heart_plus_24px)
                },
                tint = if (media.isInLibrary) {
                    Color.Red
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun TagCell(type: MediaType, isShowing: Boolean) {
    Box(Modifier.padding(vertical = 5.dp)) {
        if (isShowing) {
            JTag(type)
        }
    }
}

@Composable
private fun SynopsisCell(text: String?) {
    Text(
        text = if (text.isNotNull()) {
            text!!
        } else {
            R.string.empty_description.localized()
        },
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.outline
    )
}