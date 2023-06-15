package fr.jaetan.jmedia.home.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jaetan.core.models.data.WorkType
import fr.jaetan.core.services.MainViewModel
import fr.jaetan.jmedia.HomeNavigator
import fr.jaetan.jmedia.ui.theme.Green
import fr.jaetan.jmedia.ui.theme.Red
import fr.jaetan.jmedia.ui.widgets.JBottomSheet
import fr.jaetan.jmedia.ui.widgets.JScaledContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen.HomeViewContent() {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(30.dp),
    verticalArrangement = Arrangement.Center
    ) {
        items(MainViewModel.state.showingWorkTypes) {
            ListItem(Modifier.animateItemPlacement(), it)
        }
    }
}

@Composable
private fun HomeScreen.ListItem(modifier: Modifier, workType: WorkType) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    JScaledContent(
        modifier = modifier.fillMaxWidth(),
        onPressed = { HomeNavigator.work.openActivity(context, workType) },
        onLongPressed ={ viewModel.showBottomSheet = true },
        pressedScale = .9f
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(workType.getBackgroundColor(isDarkTheme))
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

@Composable
fun HomeScreen.BottomSheetView() {
    JBottomSheet(
        isVisible = viewModel.showBottomSheet,
        dismiss = { viewModel.showBottomSheet = false },
    ) {
        BottomSheetWorkSelector()
    }
}

@Composable
fun HomeScreen.BottomSheetWorkSelector() {
    Column {
        WorkType.all.forEach {
            BottomSheetWorkSelectorItem(it)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen.BottomSheetWorkSelectorItem(workType: WorkType) {
    val context = LocalContext.current
    val isChecked = MainViewModel.state.showingWorkTypes.contains(workType)
    val color = if (isChecked) {
        Green
    } else {
        Red
    }

    Row(Modifier
        .fillMaxWidth()
        .clickable { MainViewModel.state.workCategoryToggle(context, workType) }
        .padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        AnimatedContent(
            label = "",
            targetState = isChecked,
            transitionSpec = {
                EnterTransition.None with ExitTransition.None
            }
        ) {
            Icon(
                imageVector = if (isChecked) {
                    Icons.Rounded.Done
                } else {
                    Icons.Rounded.Clear
                },
                contentDescription = null,
                tint = color,
                modifier = Modifier.animateEnterExit(
                    enter = scaleIn(),
                    exit = ExitTransition.None
                )
            )
        }

        Spacer(Modifier.width(40.dp))

        Text(
            text = stringResource(workType.textRes),
            fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal
        )
    }
}