package fr.jaetan.jmedia.core.models

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.core.extensions.isNotNull
import fr.jaetan.jmedia.core.extensions.removeNullValues
import fr.jaetan.jmedia.ui.theme.JColor

enum class WorkType(
    @StringRes val textRes: Int,
    @StringRes val titleRes: Int,
    private val backgroundColor: Color,
    val implemented: Boolean
) {
    Manga(R.string.mangas, R.string.my_mangas, JColor.Red, true),
    Book(R.string.books, R.string.my_books, JColor.Orange, false),
    Anime(R.string.animes, R.string.my_animes, JColor.VeryLightGreen, false),
    Serie(R.string.series, R.string.my_series, JColor.Green, false),
    Movie(R.string.movies, R.string.my_movies, JColor.Pink, false);

    @Composable
    fun getBackgroundColor(isDarkTheme: Boolean): Color {
        if (isDarkTheme) return backgroundColor.copy(alpha = .7f)
        return backgroundColor
    }

    companion object {
        val all: List<WorkType> = WorkType.values().toList()

        fun toList(values: String?): List<WorkType>? {
            if (values?.isBlank() == true) return emptyList()
            if (values.isNotNull()) {
                return values!!.split(", ").map { getFromString(it) }.removeNullValues()
            }
            return null
        }

        fun getFromString(name: String?): WorkType? = all.find { it.name == name }

        const val key = "work_type"
    }
}

fun List<WorkType>.toStringList(): String = this.joinToString { it.name }