package fr.jaetan.jmedia.app.work_detail.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.work_detail.WorkDetailView

@Composable
fun WorkDetailView.ContentView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CoverImageCell()
        MainInfo()
    }
}

@Composable
fun WorkDetailView.CoverImageCell() {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = screenHeight / 2

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(viewModel.work.image.largeImageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .build(),
        contentDescription = "Image chargée",
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .blur(5.dp),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter
    )
}

@Composable
fun WorkDetailView.MainInfo() {
    Box(
        modifier = Modifier
            .offset(y = (-100).dp)
            .fillMaxWidth()
            .height(100.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background.copy(alpha = 0.0f),// Transparent au top
                        MaterialTheme.colorScheme.background.copy(alpha = 1.0f)  // Opaque au bottom
                    )
                ),
                shape = RectangleShape
            )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-50).dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = viewModel.work.title, )
    }
}