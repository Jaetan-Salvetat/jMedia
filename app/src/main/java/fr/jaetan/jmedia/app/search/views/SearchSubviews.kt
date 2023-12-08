package fr.jaetan.jmedia.app.search.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import fr.jaetan.jmedia.app.search.SearchView
import fr.jaetan.jmedia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView.TopBarView() {
    val focusManager = LocalFocusManager.current
    val search = {
        viewModel.fetchWorks()
        focusManager.clearFocus()
    }

    TopAppBar(
        title = {
            TextField(
                value = viewModel.searchValue,
                onValueChange = { viewModel.searchValue = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.research)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = { search() }) {
                        Icon(Icons.Default.Search, null)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { search() })
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, null)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}