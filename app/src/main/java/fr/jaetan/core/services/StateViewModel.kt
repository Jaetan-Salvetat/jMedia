package fr.jaetan.core.services

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.core.extensions.isNotNull
import fr.jaetan.core.extensions.toStringOrNull
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.data.works.toStringList
import kotlinx.coroutines.launch

class StateViewModel: ViewModel() {
    val showingWorkTypes = mutableStateListOf<WorkType>()

    private val Context.settings by preferencesDataStore(settingsDataStoreKey)

    fun initialize(context: Context) {
        viewModelScope.launch {
            context.settings.data.collect {
                setWorksType(WorkType.toList(it[worksTypeKey].toStringOrNull()))
            }
        }
    }

    fun workCategoryToggle(context: Context, workType: WorkType) {
        viewModelScope.launch {
            val values = showingWorkTypes
            if (values.contains(workType)) {
                if (showingWorkTypes.size <= 1) return@launch
                values.remove(workType)
            } else {
                values.add(workType)
            }

            context.settings.edit { pref ->
                pref[worksTypeKey] = values.toStringList()
            }
        }
    }

    private fun setWorksType(worksType: List<WorkType>?) {
        showingWorkTypes.clear()

        if (worksType.isNotNull()) {
            showingWorkTypes.addAll(worksType!!)
            return
        }

        showingWorkTypes.addAll(WorkType.all)
    }

    companion object {
        // Keys
        private const val settingsDataStoreKey = "settings_data_store_key"
        private val worksTypeKey = stringPreferencesKey("works_type")
    }
}