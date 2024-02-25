package fr.jaetan.jmedia.app.work_detail.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.app.work_detail.WorkDetailView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkDetailView.TopBarView() {
    TopAppBar(
        modifier = Modifier
            .background(
                color = Color.Black.copy(alpha = 0.05f),
                shape = RectangleShape
            )
            .fillMaxWidth(),
        title = {  },
        navigationIcon = {
            IconButton(
                onClick = { navController?.popBackStack() },
                content = { Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black.copy(alpha = 0.2f)
        ),
        actions = {
            IconButton(onClick = { /*TODO: Ask if the user is sure about removing the work to the library and then remove it*/ }) {
                Icon(
                    painter = if (viewModel.work.isInLibrary) {
                        painterResource(R.drawable.heart_minus_24px)
                    } else {
                        painterResource(R.drawable.heart_plus_24px)
                    },
                    contentDescription = null,
                )
            }
            IconButton(onClick = { /*TODO: Open share component and define the data to share. MAKE IT LATER*/ }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
        }
    )
}