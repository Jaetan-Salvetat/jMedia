package fr.jaetan.jmedia.search.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fr.jaetan.core.models.data.works.IWork
import fr.jaetan.core.models.ui.ListState
import fr.jaetan.jmedia.R

@Composable
fun SearchScreen.ContentView() {
    when (viewModel.state) {
        ListState.Initial -> Box(Modifier)
        ListState.Loading -> LoadingState()
        ListState.Data -> DataList()
        ListState.Empty -> EmptyState()
        ListState.Error -> ErrorState()
    }
}

@Composable
private fun SearchScreen.LoadingState() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        Alignment.BottomCenter
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SearchScreen.EmptyState() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("(\u2060╯\u2060︵\u2060╰\u2060,\u2060)", fontSize = 30.sp)
        Text(
            text = stringResource(R.string.empty_manga_search),
            fontStyle = FontStyle.Italic,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
private fun SearchScreen.ErrorState() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        Alignment.BottomCenter
    ) {
    }
}

@Composable
private fun SearchScreen.DataList() {
    LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(5.dp)) {
        items(viewModel.works, key = { it.id }) {
            DataListItem(it)
        }
    }
}

@Composable
private fun SearchScreen.DataListItem(work: IWork) {
    Box(
        Modifier
            .width(150.dp)
            .height(195.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { }) {
        
        WorkImage(work)

        Box(
            Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Gray.copy(alpha = 0f), Color.Gray)
                    )
                )
                .padding(horizontal = 10.dp)
                .padding(bottom = 5.dp, top = 20.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(work.title, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}


@Composable
private fun SearchScreen.WorkImage(work: IWork) {
    val imagePainter = rememberAsyncImagePainter(model = work.coverImageUrl)

    Image(
        painter = imagePainter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}
