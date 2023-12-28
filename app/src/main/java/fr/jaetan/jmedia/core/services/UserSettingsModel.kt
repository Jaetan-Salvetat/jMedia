package fr.jaetan.jmedia.core.services

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.core.models.UserSettings
import fr.jaetan.jmedia.core.models.WorkType

class UserSettingsModel: ViewModel() {
    private val Context.userSettings by preferencesDataStore(UserSettingsKeys.SETTINGS_KEY)
    var settings by mutableStateOf(UserSettings())
    // var workTypes = mutableStateListOf<WorkType>()

    suspend fun initialize(context: Context) {
        context.userSettings.data.collect { prefs ->
            prefs[UserSettingsKeys.workTypes]?.let {
                settings = settings.copy(selectedWorkTypes = WorkType.fromStringSet(it))
            }
        }
    }

    suspend fun setWorkTypes(context: Context, types: List<WorkType>) {
        context.userSettings.edit {  prefs ->
            prefs[UserSettingsKeys.workTypes] = types.map { it.name }.toSet()
        }
    }
}

private object UserSettingsKeys {
    const val SETTINGS_KEY = "user_settings"

    val workTypes = stringSetPreferencesKey("work_types")
}