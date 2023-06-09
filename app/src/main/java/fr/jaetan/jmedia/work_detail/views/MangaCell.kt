package fr.jaetan.jmedia.work_detail.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.core.models.data.works.WorkGenre
import fr.jaetan.jmedia.R

@Composable
fun WorkDetailScreen.MangaCell() {
    Column(Modifier.padding(top = 20.dp)) {
        GenresListManga()
        StateCellManga()
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