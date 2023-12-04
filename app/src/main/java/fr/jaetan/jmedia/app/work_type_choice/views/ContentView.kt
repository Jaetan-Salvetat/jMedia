package fr.jaetan.jmedia.app.work_type_choice.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.jmedia.app.work_type_choice.WorkTypeChoiceView
import fr.jaetan.jmedia.core.models.WorkType
import fr.jaetan.jmedia.ui.widgets.JScaledContent

@Composable
fun WorkTypeChoiceView.ContentView() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        items(WorkType.all.sortedByDescending { it.implemented }) {
            ListItem(it)
        }
    }
}

@Composable
private fun WorkTypeChoiceView.ListItem(workType: WorkType) {
    JScaledContent(
        modifier = Modifier.fillMaxWidth(),
        onPressed = { viewModel.setWorkType(workType) },
        pressedScale = .9f
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(workType.getBackgroundColor())
                .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(workType.textRes),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}