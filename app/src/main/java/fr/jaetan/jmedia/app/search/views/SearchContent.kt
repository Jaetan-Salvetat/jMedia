package fr.jaetan.jmedia.app.search.views

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.core.models.ListState
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
    val configuration = LocalConfiguration.current

    val offsetX = remember { Animatable(0f, 30f) }
    val scope = rememberCoroutineScope()
    val state = rememberDraggableState { delta ->
        val newValue = offsetX.value + delta
        scope.launch { offsetX.animateTo(newValue) }
    }
    val onDragStopped: CoroutineScope.(Float) -> Unit = {
        val screenWidth = -configuration.screenWidthDp.toFloat()

        if (offsetX.value.roundToInt() < screenWidth / 2) {
            scope.launch { offsetX.animateTo(screenWidth) }
        } else {
            scope.launch { offsetX.animateTo(0f) }
        }
    }

    Box(Modifier.background(MaterialTheme.colorScheme.scrim)) {
        // Action buttons
        Row(Modifier.align(Alignment.CenterEnd)) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .clickable {  }
                    .padding(horizontal = 30.dp)
            ) {
                Icon(Icons.Filled.Favorite, null)
            }
        }

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
                .padding(vertical = 15.dp, horizontal = 20.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(work.image.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(work.title, modifier = Modifier.padding(horizontal = 10.dp))
        }
    }
}