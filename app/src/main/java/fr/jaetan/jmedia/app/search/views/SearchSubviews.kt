package fr.jaetan.jmedia.app.search.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import fr.jaetan.jmedia.app.search.SearchView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView.TopBarView() {
    val focusRequester = FocusRequester()

    SideEffect {
        focusRequester.requestFocus()
    }

    TopAppBar(
        title = {
            TextField(
                value = viewModel.searchValue,
                onValueChange = { viewModel.searchValue = it },
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = { viewModel.fetchWorks() }) {
                        Icon(Icons.Default.Search, null)
                    }
                }
            )
        }
    )
}