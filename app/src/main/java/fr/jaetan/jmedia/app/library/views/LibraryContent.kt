package fr.jaetan.jmedia.app.library.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.library.LibraryView
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.core.services.Navigator
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.shared.Image


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryView.ContentView() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        WorkType.all.forEach { workType ->
            val controller = MainViewModel.getController(workType)

            if (controller.localWorks.isNotEmpty()) {
                stickyHeader {
                    Text(
                        text = stringResource(workType.titleRes),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 20.sp
                    )
                }
                item { MediaCarousel(controller.localWorks) }
                item { Spacer(modifier = Modifier.height(16.dp))}
            }
        }
    }
}

@Composable
fun LibraryView.MediaCarousel(medias: List<IWork>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(medias.take(10)) {
            MediaItem(it)
        }
    }
}

@Composable
fun LibraryView.MediaItem(work: IWork) {
    Surface(
        modifier = Modifier
            .width(130.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { navController?.navigate(Navigator.workDetail.getNavRoute(work.id.toHexString(), work.type)) },
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp
    ) {
        Box {
            ImageCell(work.image)
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.surface.copy(alpha = 0f), MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                            startY = 0f,
                            endY = with(LocalDensity.current) { 50.dp.toPx() } // Hauteur du tiers en pixels
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 16.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = work.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Composable
private fun ImageCell(image: Image) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.imageUrl)
            .crossfade(true)
            .error(R.drawable.placeholder)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    )
}
