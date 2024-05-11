package fr.jaetan.jmedia.ui.shared.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.models.medias.IMedia
import fr.jaetan.jmedia.models.medias.shared.Image

@Composable
fun GridMediasList(medias: List<IMedia>, onClick: () -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(medias) { GridItem(it, onClick) }
    }
}

@Composable
private fun GridItem(media: IMedia, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        ImageCell(media.image)
        TitleCell(media.title)
    }
}

@Composable
private fun ImageCell(image: Image?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image?.imageUrl)
            .crossfade(true)
            .error(R.drawable.placeholder)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    )
}

@Composable
private fun TitleCell(title: String) {
    Text(
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        style = MaterialTheme.typography.bodyLarge
    )
}