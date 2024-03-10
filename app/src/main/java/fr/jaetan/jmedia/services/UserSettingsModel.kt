package fr.jaetan.jmedia.services

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.ui.theme.themes.JTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserSettingsModel {
    private val Context.userSettings by preferencesDataStore(UserSettingsKeys.SETTINGS_KEY)
    private var mainJob: Job? = null
    var selectedWorkTypes = mutableStateListOf<WorkType>()
    var currentTheme by mutableStateOf(JTheme.Default)

    suspend fun initialize(context: Context) {
        mainJob?.cancel()

        mainJob = CoroutineScope(Dispatchers.IO).launch {
            context.userSettings.data.collect { prefs ->
                prefs[UserSettingsKeys.workTypes]?.let {
                    selectedWorkTypes.clear()
                    selectedWorkTypes.addAll(WorkType.fromStringSet(it))
                }

                prefs[UserSettingsKeys.currentTheme]?.let {
                    currentTheme = JTheme.fromString(it)
                }
            }
        }
    }

    suspend fun setWorkTypes(context: Context, types: List<WorkType>) {
        context.userSettings.edit { prefs ->
            prefs[UserSettingsKeys.workTypes] = types.map { it.name }.toSet()
        }
    }

    suspend fun setTheme(context: Context, theme: JTheme) {
        context.userSettings.edit { prefs ->
            prefs[UserSettingsKeys.currentTheme] = theme.name
        }
    }

    suspend fun clearUserPreferences(context: Context) {
        context.userSettings.edit {
            it[UserSettingsKeys.workTypes] = setOf()
            it[UserSettingsKeys.currentTheme] = JTheme.Default.name
        }
    }
}

private object UserSettingsKeys {
    const val SETTINGS_KEY = "user_settings"

    val workTypes = stringSetPreferencesKey("work_types")
    val currentTheme = stringPreferencesKey("current_theme")
}