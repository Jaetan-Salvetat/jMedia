package fr.jaetan.jmedia.app.search.views

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.core.extensions.isNotNull
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.Smiley
import fr.jaetan.jmedia.core.models.works.Manga
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
            else -> InfoCell(Smiley.Sad, R.string.request_error_message)
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

@Composable
private fun SearchView.WorksList() {
    LazyColumn {
        items(viewModel.works) {
            WorksListItem(it)
        }
    }
}

@Composable
private fun SearchView.WorksListItem(work: Manga) {
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current
    val actionsButtonsSize = 70.dp
    var hasVibrate = false

    val offsetX = remember { Animatable(0f, Spring.StiffnessLow) }
    val scope = rememberCoroutineScope()
    val state = rememberDraggableState { delta ->
        with(density) {
            val newValue = offsetX.value + delta

            if (newValue.roundToInt().toDp() < -actionsButtonsSize / 2 && !hasVibrate) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                hasVibrate = true
            }

            scope.launch { offsetX.animateTo(newValue) }
        }
    }
    val onDragStopped: CoroutineScope.(Float) -> Unit = {
        hasVibrate = false

        with(density) {
            if (offsetX.value.roundToInt().toDp() < -actionsButtonsSize / 2) {
                scope.launch { offsetX.animateTo(-actionsButtonsSize.toPx()) }
            } else {
                scope.launch { offsetX.animateTo(0f) }
            }
        }
    }

    Box(Modifier.height(140.dp)) {
        // Action button
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.scrim)
                .width(actionsButtonsSize)
                .align(Alignment.CenterEnd)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { viewModel.mangaLibraryHandler(work) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = if (viewModel.localWorks.find { it.title == work.title }.isNotNull()) {
                        painterResource(R.drawable.heart_minus_24px)
                    } else {
                        painterResource(R.drawable.heart_plus_24px)
                    },
                    contentDescription = null
                )
            }
        }

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
                ImageCell(work)

                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = work.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 10.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = if (work.synopsis.isNotNull()) {
                            work.synopsis!!
                        } else {
                            stringResource(R.string.empty_description)
                        },
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Divider()
        }
    }
}

@Composable
private fun ImageCell(work: Manga) {
    if (work.image.bitmap.isNotNull()) {
        Image(
            bitmap = work.image.bitmap!!.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
        )
    } else {
        AsyncImage(
            model = work.image.smallImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
        )
    }
}