package fr.jaetan.jmedia.search.views

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.search.SearchViewModel

class SearchScreen(
    private val activity: Activity,
    override val viewModel: SearchViewModel
): Screen<SearchViewModel>() {
    @Composable
    override fun TopBar() {
        val focusRequester = FocusRequester()
        val focusManager = LocalFocusManager.current
        val search = {
            viewModel.searchWork()
            focusManager.clearFocus()
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .focusRequester(focusRequester)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)) {
                OutlinedTextField(
                    value = viewModel.searchValue,
                    onValueChange = { viewModel.searchValue = it },
                    singleLine = true,
                    placeholder = { Text(stringResource(R.string.search_placeholder)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    leadingIcon = {
                        IconButton(onClick = { activity.finish() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = search) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onDone = { search() }
                    )
                )
            }
            Divider()
        }
    }

    @Composable
    override fun Content() {
        ContentView()
    }
}