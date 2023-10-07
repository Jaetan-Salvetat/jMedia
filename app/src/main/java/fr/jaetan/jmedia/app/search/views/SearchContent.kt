package fr.jaetan.jmedia.app.search.views

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.jaetan.jmedia.ui.shared.ProgressView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView.ContentView() {
    SearchBar(
        query = viewModel.searchValue,
        onQueryChange = { viewModel.searchValue = it },
        onSearch = { /* TODO: Start the research */ },
        active = viewModel.searchBarIsActive,
        onActiveChange = {
            viewModel.searchBarIsActive = it
        }
    ) {
        ProgressView()
    }
}

@Composable
private fun SearchView.WorksList() {

}