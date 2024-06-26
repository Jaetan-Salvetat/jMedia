package fr.jaetan.jmedia.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.jmedia.models.medias.shared.MediaType

@Composable
fun JTag(type: MediaType) {
    Box(
        Modifier
            .clip(CircleShape)
            .background(type.getBackgroundColor())
            .padding(vertical = 5.dp, horizontal = 10.dp)) {
        Text(
            text = stringResource(type.textRes),
            fontSize = 12.sp,
            color = Color.White
        )
    }
}