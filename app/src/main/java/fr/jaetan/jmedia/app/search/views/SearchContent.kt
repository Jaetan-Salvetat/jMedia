package fr.jaetan.jmedia.app.search.views

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.Smiley
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.Image
import fr.jaetan.jmedia.ui.shared.JTag
import fr.jaetan.jmedia.ui.widgets.JScaledContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SearchView.ContentView() {
     Column {
        when (viewModel.listState) {
            ListState.Default -> InfoCell(Smiley.Smile, R.string.default_search_text)
            ListState.Loading -> LoadingState()
            ListState.HasData -> WorksList()
            ListState.EmptyData -> InfoCell(Smiley.Surprise, R.string.empty_search)
        }
    }
}

@Composable
private fun InfoCell(smiley: Smiley, @StringRes message: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = smiley.text,
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = stringResource(message),
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
private fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchView.WorksList() {
    LazyColumn {
        items(viewModel.works, key = { "${it.title}_${it.type}" }) {
            WorksListItem(it, Modifier.animateItemPlacement())
        }
    }
}

@Composable
private fun SearchView.WorksListItem(work: IWork, modifier: Modifier) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current
    val actionsButtonsSize = 70.dp

    // States
    var hasVibrate by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val actionButtonColor by animateColorAsState(
        targetValue = if (hasVibrate) {
            Color.Red
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
                viewModel.libraryHandler(work)
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
        ActionButton(work, actionsButtonsSize, actionButtonScale, Modifier.align(Alignment.CenterEnd))

        // Work details
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
                    .background(MaterialTheme.colorScheme.background)
                    .clickable { }
                    .padding(start = 20.dp)
                    .padding(vertical = 15.dp)
            ) {
                ImageCell(work.image)

                Column(Modifier.padding(horizontal = 20.dp)) {
                    WorkTitleCell(work)
                    TagCell(work.type)
                    SynopsisCell(work.synopsis)
                }
            }

            Divider()
        }
    }
}

@Composable
private fun ActionButton(work: IWork, buttonSize: Dp, iconScale: Float, modifier: Modifier) {
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
                painter = if (work.isInLibrary) {
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
private fun ImageCell(image: Image) {
    if (image.bitmap.isNotNull()) {
        Image(
            bitmap = image.bitmap!!.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
        )
    } else {
        AsyncImage(
            model = image.smallImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
        )
    }
}

@Composable
private fun SearchView.WorkTitleCell(work: IWork) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = work.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        JScaledContent(
            onPressed = { viewModel.libraryHandler(work) },
            modifier = Modifier.padding(horizontal = 10.dp)) {
            Icon(
                painter = if (work.isInLibrary) {
                    painterResource(R.drawable.heart_minus_24px)
                } else {
                    painterResource(R.drawable.heart_plus_24px)
                },
                tint = if (work.isInLibrary) {
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
private fun SearchView.TagCell(type: WorkType) {
    Box(Modifier.padding(vertical = 5.dp)) {
        if (viewModel.filters.size > 1) {
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
            stringResource(R.string.empty_description)
        },
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.outline
    )
}