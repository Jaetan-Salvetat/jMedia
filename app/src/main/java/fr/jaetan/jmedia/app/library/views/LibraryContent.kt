package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.Manga
import kotlinx.coroutines.launch

@Composable
fun LibraryView.PageContent(page: Int) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(viewModel.mangaList.size, key = {it}) { index ->
            val manga = viewModel.mangaList[index]
            // Appeler votre fonction composable pour afficher un élément de la grille
            MangaGridItem(manga = manga) {
                // Gérer l'événement de clic sur un manga
                // TODO
            }
        }
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
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)),
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

        HorizontalPager(state = pagerState, userScrollEnabled = true) {
            PageContent(it)
        }
    }
}

@Composable
fun MangaGridItem(manga: Manga, onItemClick: (Manga) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onItemClick(manga) }
    ) {
        // Image avec fondu vers le bas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            AsyncImage(
                model = manga.image.smallImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = with(LocalDensity.current) { 170.dp.toPx() },
                            endY = with(LocalDensity.current) { 220.dp.toPx() }
                        )
                    )
            )
        }

        // Texte avec fond noir
        Text(
            text = manga.title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White, // Couleur du texte blanc
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 10.dp)
                .align(Alignment.BottomCenter)
        )
    }
}