package fr.jaetan.jmedia.app.search.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.locals.LocalMediaManager
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.Smiley
import fr.jaetan.jmedia.ui.shared.InfoCell
import fr.jaetan.jmedia.ui.shared.VerticalMediasListItem
import kotlinx.coroutines.launch

@Composable
fun SearchView.ContentView() {
    val mediaManager = LocalMediaManager.current
    val searchState by mediaManager.searchState.collectAsState()

    when (searchState) {
        ListState.Default -> InfoCell(Smiley.Smile, R.string.default_search_text)
        ListState.Loading -> MediaList()
        ListState.HasData -> MediaList()
        ListState.EmptyData -> InfoCell(Smiley.Surprise, R.string.empty_search)
    }
}

@Composable
private fun SearchView.MediaList() {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val mediaManager = LocalMediaManager.current
    val mediasAsList = mediaManager.getFetchedMedias(viewModel.sort, viewModel.sortDirection, viewModel.filters)

    val scrollToTop: () -> Unit = {
        scope.launch {
            listState.animateScrollToItem(0)
        }
    }

    LazyColumn(state = listState) {
        items(mediasAsList, key = { it.id.toHexString() }) {
            VerticalMediasListItem(it, Modifier.animateItem())
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