package fr.jaetan.jmedia.app.library.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.extensions.localized
import fr.jaetan.jmedia.locals.LocalMediaManager
import fr.jaetan.jmedia.models.Smiley
import fr.jaetan.jmedia.models.medias.IMedia
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.ui.shared.InfoCell
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryView.ContentView() {
    val mediaManager = LocalMediaManager.current
    val localMedias by mediaManager.localMedias.collectAsState(mapOf())
    val pagerState = rememberPagerState(pageCount = { MediaType.entries.size })
    val currentType = localMedias.toList().getOrNull(pagerState.currentPage)?.first

    when {
       mediaManager.count <= 0 || currentType == null -> InfoCell(Smiley.Surprise, R.string.empty_library)
        else -> Column {
            TabBar(localMedias, pagerState)
            PageContent(currentType, pagerState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryView.TabBar(medias: Map<MediaType, List<IMedia>?>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val onNavigate: (toIndex: Int) -> Unit = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(it)
        }
    }

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = {
            TabIndicator(
                tabIndex = pagerState.currentPage,
                tabPositions = it
            )
        }
    ) {
        medias.entries.forEachIndexed { index, entry ->
            val backgroundColor by animateColorAsState(
                targetValue = if (index == pagerState.currentPage) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    Color.Transparent
                },
                label = ""
            )

            entry.value?.let {
                Tab(
                    selected = index == viewModel.currentTabIndex,
                    onClick = { onNavigate(index) },
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 5.dp)
                        .padding(bottom = 10.dp)
                        .background(backgroundColor, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text(entry.key.textRes.localized())
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PageContent(type: MediaType, pagerState: PagerState) {
    HorizontalPager(state = pagerState) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(type.textRes.localized())
        }
    }
}

@Composable
private fun TabIndicator(tabIndex: Int, tabPositions: List<TabPosition>) {
    val currentTab = tabPositions[tabIndex]

    Box(
        modifier = Modifier
            .tabIndicatorOffset(currentTab)
            .height(2.dp)
            .width(currentTab.width)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.primaryContainer, CircleShape)
    )
}