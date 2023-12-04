package fr.jaetan.jmedia.app.search.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.core.models.ListState
import fr.jaetan.jmedia.core.models.works.Manga

@Composable
fun SearchView.ContentView() {
    when (viewModel.listState) {
        ListState.HasData -> WorksList()
        ListState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> Box(Modifier.fillMaxSize())
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
private fun SearchView.WorksListItem(work: Manga) {
    Row {
        Text(work.title)
    }
}