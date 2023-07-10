package fr.jaetan.jmedia.work_detail.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fr.jaetan.core.models.data.works.WorkGenre
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.ui.ListState
import fr.jaetan.jmedia.R

@Composable
fun WorkDetailScreen.ContentView() = when (viewModel.state) {
    ListState.Loading -> LoadingState()
    ListState.Data -> WorkDetail()
    else -> {}
}

@Composable
private fun WorkDetailScreen.LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun WorkDetailScreen.WorkDetail() {
    Column {
        Header()
        ContentCell()
        Description()
    }
}

@Composable
private fun WorkDetailScreen.Header() {
    val work = viewModel.work
    val imagePainter = rememberAsyncImagePainter(work?.coverImageUrl)

    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp)) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().blur(10.dp)
        )

        TitleSection()
    }
}

@Composable
private fun WorkDetailScreen.TitleSection() {
    val work = viewModel.work
    val imagePainter = rememberAsyncImagePainter(work?.coverImageUrl)
    val background = MaterialTheme.colorScheme.background
    val imageHeight = 143.dp

    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(background.copy(alpha = .3f), background)
                )
            )
    ) {
        Row(
            Modifier
                .padding(horizontal = 15.dp)
                .align(Alignment.BottomStart)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(imageHeight)
                    .clip(RoundedCornerShape(15.dp))
            )

            Column(
                modifier = Modifier
                    .height(imageHeight)
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                work?.title?.let {
                    Text(it, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                }

                Authors()
            }
        }
    }
}

@Composable
private fun WorkDetailScreen.Authors() {
    Log.d("testt", viewModel.work!!.authors.toString())
    if (viewModel.work?.authors?.isEmpty() == true) {
        Text(stringResource(R.string.unknown_author))
    } else {
        Row {
            viewModel.work?.authors?.forEachIndexed { index, workAuthor ->
                val name = if (index == 0) {
                    workAuthor.name
                } else {
                    ", ${workAuthor.name}"
                }

                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun WorkDetailScreen.Description() {
    Column(Modifier.padding(horizontal = 15.dp)) {
        Divider(Modifier.padding(vertical = 20.dp))

        viewModel.work?.description?.let {
            Text(text = it)
        }
    }
}

@Composable
private fun WorkDetailScreen.ContentCell() {
    when (viewModel.workType) {
        WorkType.Manga -> Column(Modifier.padding(top = 20.dp)) {
            GenresListManga()
            StateCellManga()
        }
    }
}

@Composable
private fun WorkDetailScreen.StateCellManga() {
    // val work = viewModel.work as Manga?

    Column(Modifier.padding(horizontal = 15.dp)) {
        Divider(Modifier.padding(vertical = 20.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(R.string.vf), fontWeight = FontWeight.Bold)
                Row {
                    Text(
                        text = stringResource(R.string.in_progress),
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " • ",
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp
                    )
                    Text(
                        text = stringResource(R.string.x_tomes, 12),
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp
                    )
                }
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(R.string.vo), fontWeight = FontWeight.Bold)
                Row {
                    Text(
                        text = stringResource(R.string.finished),
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " • ",
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp
                    )
                    Text(
                        text = stringResource(R.string.x_tomes, 15),
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun WorkDetailScreen.GenresListManga() {
    // var genres = (viewModel.work!! as Manga).genres
    val genres = listOf(
        WorkGenre("Drame"),
        WorkGenre("Humour"),
        WorkGenre("Psychologique"),
        WorkGenre("Slice of Life"),
        WorkGenre("Horreur"),
    )

    LazyRow(contentPadding = PaddingValues(horizontal = 10.dp)) {
        items(genres.size) {
            Row {
                if (it != 0) {
                    Box(Modifier.width(10.dp))
                }
                GenresMangaListItem(genres[it].name)
            }
        }
    }
}

@Composable
fun WorkDetailScreen.GenresMangaListItem(name: String) {
    val shape = RoundedCornerShape(10.dp)

    Box(
        Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, shape)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(name)
    }
}