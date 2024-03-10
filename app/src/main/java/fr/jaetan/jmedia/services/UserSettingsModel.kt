package fr.jaetan.jmedia.services

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fr.jaetan.jmedia.models.JColorScheme
import fr.jaetan.jmedia.models.JTheme
import fr.jaetan.jmedia.models.works.shared.WorkType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserSettingsModel {
    private val Context.userSettings by preferencesDataStore(UserSettingsKeys.SETTINGS_KEY)
    private var mainJob: Job? = null
    var selectedWorkTypes = mutableStateListOf<WorkType>()
    var currentColorScheme by mutableStateOf(JColorScheme.Default)
    var currentTheme by mutableStateOf(JTheme.System)
    var isPureDark by mutableStateOf(false)

    suspend fun initialize(context: Context) {
        mainJob?.cancel()

        mainJob = CoroutineScope(Dispatchers.IO).launch {
            context.userSettings.data.collect { prefs ->
                prefs[UserSettingsKeys.workTypes]?.let {
                    selectedWorkTypes.clear()
                    selectedWorkTypes.addAll(WorkType.fromStringSet(it))
                }

                currentColorScheme = JColorScheme.fromString(prefs[UserSettingsKeys.currentColorScheme])
                currentTheme = JTheme.fromString(prefs[UserSettingsKeys.currentTheme])
                isPureDark = prefs[UserSettingsKeys.isPurDark] ?: false
            }
        }
    }

    suspend fun setWorkTypes(context: Context, types: List<WorkType>) {
        context.userSettings.edit { prefs ->
            prefs[UserSettingsKeys.workTypes] = types.map { it.name }.toSet()
        }
    }

    suspend fun setColorScheme(context: Context, theme: JColorScheme) {
        context.userSettings.edit { prefs ->
            prefs[UserSettingsKeys.currentColorScheme] = theme.name
        }
    }

    suspend fun setTheme(context: Context, theme: JTheme) {
        context.userSettings.edit { prefs ->
            prefs[UserSettingsKeys.currentTheme] = theme.name
        }
    }

    suspend fun setPurDark(context: Context, isPurDark: Boolean) {
        context.userSettings.edit { prefs ->
            prefs[UserSettingsKeys.isPurDark] = isPurDark
        }
    }

    suspend fun clearUserPreferences(context: Context) {
        context.userSettings.edit {
            it[UserSettingsKeys.workTypes] = setOf()
            it[UserSettingsKeys.currentColorScheme] = JColorScheme.Default.name
            it[UserSettingsKeys.currentTheme] = JTheme.System.name
            it[UserSettingsKeys.isPurDark] = false
        }
    }
}

private object UserSettingsKeys {
    const val SETTINGS_KEY = "user_settings"

    val workTypes = stringSetPreferencesKey("work_types")
    val currentColorScheme = stringPreferencesKey("current_color_scheme")
    val currentTheme = stringPreferencesKey("current_theme")
    val isPurDark = booleanPreferencesKey("is_pur_dark")
}