package fr.jaetan.jmedia.core.models

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.ui.theme.JColor

enum class WorkType(
    @StringRes val textRes: Int,
    @StringRes val titleRes: Int,
    private val backgroundColor: Color,
    val implemented: Boolean
) {
    Manga(R.string.mangas, R.string.my_mangas, JColor.Red, true),
    Anime(R.string.animes, R.string.my_animes, JColor.VeryLightGreen, false),
    Book(R.string.books, R.string.my_books, JColor.Orange, false),
    Serie(R.string.series, R.string.my_series, JColor.Green, false),
    Movie(R.string.movies, R.string.my_movies, JColor.Pink, false);

    @Composable
    fun getBackgroundColor(): Color {
        val isDarkTheme = isSystemInDarkTheme()

        return when {
            isDarkTheme -> backgroundColor.copy(alpha = .7f)
            else -> backgroundColor
        }
    }

    companion object {
        val all: List<WorkType> = WorkType.values().toList()
    }
}
