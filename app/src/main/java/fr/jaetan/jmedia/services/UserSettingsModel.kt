package fr.jaetan.jmedia.services

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fr.jaetan.jmedia.models.works.shared.WorkType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserSettingsModel {
    private val Context.userSettings by preferencesDataStore(UserSettingsKeys.SETTINGS_KEY)
    private var mainJob: Job? = null
    var selectedWorkTypes = mutableStateListOf<WorkType>()

    suspend fun initialize(context: Context) {
        mainJob?.cancel()

        mainJob = CoroutineScope(Dispatchers.IO).launch {
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

    suspend fun clearUserPreferences(context: Context) {
        context.userSettings.edit {
            it[UserSettingsKeys.workTypes] = setOf()
        }
    }
}

private object UserSettingsKeys {
    const val SETTINGS_KEY = "user_settings"

    val workTypes = stringSetPreferencesKey("work_types")
}