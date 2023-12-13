package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.core.models.WorkType
import kotlinx.coroutines.launch

@Composable
fun PageContent(page: Int) {
    Column (
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(WorkType.all[page].titleRes))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryView.ContentView() {
    val implementedWorkTypes = WorkType.all
    val pagerState = rememberPagerState( pageCount = { WorkType.all.size })
    val scope = rememberCoroutineScope()

    Column {

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp
        ) {
            implementedWorkTypes.forEach { workType ->
                Tab(
                    selected = pagerState.currentPage == WorkType.all.indexOf(workType),
                    onClick = { scope.launch { pagerState.animateScrollToPage(WorkType.all.indexOf(workType)) } },
                    text = { Text(stringResource(id = workType.textRes)) }
                )
            }
        }

        HorizontalPager(state = pagerState) {
            PageContent(it)
        }
    }
}