package fr.jaetan.jmedia.search.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.jaetan.core.models.ui.ListState

@Composable
fun SearchScreen.ContentView() {
    when (viewModel.state) {
        ListState.Loading -> LoadingState()
        ListState.Data -> DataList()
        ListState.Empty -> EmptyState()
        ListState.Error -> ErrorState()
    }
}

@Composable
fun SearchScreen.LoadingState() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        Alignment.BottomCenter
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SearchScreen.EmptyState() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        Alignment.BottomCenter
    ) {
    }
}

@Composable
fun SearchScreen.ErrorState() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        Alignment.BottomCenter
    ) {
    }
}

@Composable
fun SearchScreen.DataList() {

}