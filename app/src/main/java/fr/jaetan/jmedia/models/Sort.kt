package fr.jaetan.jmedia.models

import androidx.annotation.StringRes
import fr.jaetan.jmedia.R

enum class Sort(@StringRes val textRes: Int) {
    Name(R.string.sort_by_name),
    Rating(R.string.sort_by_rating),
    Default(R.string.sort_by_default);

    companion object {
        val all = entries
    }
}

enum class SortDirection(@StringRes val textRes: Int) {
    Ascending(R.string.ascending),
    Descending(R.string.descending);

    companion object {
        val all = entries
    }
}