package fr.jaetan.jmedia.search.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jaetan.core.models.data.WorkType
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.search.SearchViewModel

class SearchScreen(workType: WorkType): Screen<SearchViewModel>() {
    override val viewModel = SearchViewModel(workType)

    @Composable
    override fun TopBar() {
        val focusRequester = FocusRequester()

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
                    maxLines = 1,
                    placeholder = { Text(stringResource(R.string.search_placeholder)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
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