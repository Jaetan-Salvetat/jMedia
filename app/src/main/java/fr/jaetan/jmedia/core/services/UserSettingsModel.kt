package fr.jaetan.jmedia.core.services

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fr.jaetan.jmedia.models.WorkType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserSettingsModel {
    private val Context.userSettings by preferencesDataStore(UserSettingsKeys.SETTINGS_KEY)
    var selectedWorkTypes = mutableStateListOf<WorkType>()

    suspend fun initialize(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            context.userSettings.data.collect { prefs ->
                prefs[UserSettingsKeys.workTypes]?.let {
                    selectedWorkTypes.clear()
                    selectedWorkTypes.addAll(WorkType.fromStringSet(it))
                }
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