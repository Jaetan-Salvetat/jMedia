package fr.jaetan.jmedia.search.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fr.jaetan.core.models.data.works.IWork
import fr.jaetan.core.models.ui.ListState
import fr.jaetan.core.services.push
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.search.SearchNavigator
import fr.jaetan.jmedia.ui.widgets.JScaledContent

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
    LazyColumn(modifier = Modifier.padding(vertical = 15.dp)) {
        items(viewModel.works, key = { it.id }) {
            DataListItem(it)
        }
    }
}

@Composable
private fun SearchScreen.DataListItem(work: IWork) {
    Column {
        JScaledContent(
            onPressed = {
                navController?.push(SearchNavigator.workDetail.getNavRoute(work.title))
            },
            pressedScale = .9f
        ) {
            Row(
                modifier = Modifier.height(110.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WorkImage(work)
                WorkTexts(work)
            }
        }

        Divider(
            Modifier
                .padding(vertical = 15.dp)
                .padding(start = 15.dp)
        )
    }
}

@Composable
private fun SearchScreen.WorkImage(work: IWork) {
    val imagePainter = rememberAsyncImagePainter(model = work.coverImageUrl)

    Image(
        painter = imagePainter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(70.dp)
            .fillMaxHeight()
            .padding(start = 15.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@Composable
private fun SearchScreen.WorkTexts(work: IWork) {
    Column(Modifier.padding(start = 15.dp)) {
        Text(
            work.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp, end = 15.dp)
        )
        Text(
            work.description,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            modifier = Modifier.padding(end = 15.dp)
        )
    }
}