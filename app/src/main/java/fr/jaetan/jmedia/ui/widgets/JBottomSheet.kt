package fr.jaetan.jmedia.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JBottomSheet(
    isVisible: Boolean,
    dismiss: () -> Unit,
    shouldBeFullScreen: Boolean = false,
    state: SheetState = rememberModalBottomSheetState(shouldBeFullScreen),
    content: @Composable ColumnScope.() -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = dismiss,
            sheetState = state,
            containerColor = if (shouldBeFullScreen) {
                MaterialTheme.colorScheme.background
            } else {
                Color.Transparent
            },
            shape = RoundedCornerShape(0.dp),
            dragHandle = {},
            tonalElevation = if (shouldBeFullScreen) 0.dp else 10.dp,
            modifier = if (!shouldBeFullScreen) {
                Modifier.Companion
                    .padding(horizontal = 10.dp)
                    .navigationBarsPadding()
                    .defaultMinSize(minHeight = 100.dp)
            } else {
                Modifier
            }
        ) {
            Column(
                Modifier
                    .padding(bottom = if (shouldBeFullScreen) 0.dp else 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(if (shouldBeFullScreen) 0.dp else 10.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                content()
            }
        }
    }
}