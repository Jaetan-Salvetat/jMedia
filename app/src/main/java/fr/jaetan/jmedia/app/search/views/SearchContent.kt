package fr.jaetan.jmedia.app.search.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.Smiley
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.ui.shared.InfoCell
import fr.jaetan.jmedia.ui.shared.JTag
import fr.jaetan.jmedia.ui.theme.JColor
import fr.jaetan.jmedia.ui.widgets.JScaledContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SearchView.ContentView() {
    when (viewModel.listState) {
        ListState.Default -> InfoCell(Smiley.Smile, R.string.default_search_text)
        ListState.Loading -> WorksList()
        ListState.HasData -> WorksList()
        ListState.EmptyData -> InfoCell(Smiley.Surprise, R.string.empty_search)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchView.WorksList() {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    val scrollToTop: () -> Unit = {
        scope.launch {
            listState.animateScrollToItem(0)
        }
    }

    LazyColumn(state = listState) {
        items(viewModel.sortedWorks, key = { it.id.toHexString() }) {
            WorksListItem(it, Modifier.animateItemPlacement())
        }

    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        AnimatedVisibility(showButton, enter = scaleIn(), exit = scaleOut()) {
            FloatingActionButton(onClick = scrollToTop, modifier = Modifier.padding(20.dp)) {
                Icon(Icons.Default.ArrowUpward, null)
            }
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

            HorizontalDivider()
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
            R.string.empty_description.localized()
        },
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.outline
    )
}