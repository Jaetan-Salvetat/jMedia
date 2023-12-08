package fr.jaetan.jmedia.app.search.views

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.core.extensions.isNotNull
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.works.Image
import fr.jaetan.jmedia.core.models.works.Manga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SearchView.ContentView() {
    when (viewModel.listState) {
        ListState.HasData -> WorksList()
        ListState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> Box {}
    }
}

@Composable
fun SearchView.WorksList() {
    LazyColumn {
        items(viewModel.works) {
            WorksListItem(it)
        }
    }
}

@Composable
private fun WorksListItem(work: Manga) {
    val density = LocalDensity.current
    val actionsButtonsSize = 100.dp

    val offsetX = remember { Animatable(0f, 30f) }
    val scope = rememberCoroutineScope()
    val state = rememberDraggableState { delta ->
        val newValue = offsetX.value + delta
        scope.launch { offsetX.animateTo(newValue) }
    }
    val onDragStopped: CoroutineScope.(Float) -> Unit = {
        with(density) {
            if (offsetX.value.roundToInt().toDp() < -actionsButtonsSize) {
                scope.launch { offsetX.animateTo(-actionsButtonsSize.toPx()) }
            } else {
                scope.launch { offsetX.animateTo(0f) }
            }
        }
    }

    Box(Modifier.height(140.dp)) {
        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.scrim)
                .width(actionsButtonsSize)
                .align(Alignment.CenterEnd)
        ) {
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f).clickable {  },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Favorite, null)
            }
        }

        // Work details
        Column(Modifier.clickable {  }) {
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
                    .padding(start = 20.dp)
                    .padding(vertical = 15.dp)
            ) {
                ImageCell(work.image)

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
private fun ImageCell(image: Image) {
    AsyncImage(
        model = image.imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.width(70.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
    )
}