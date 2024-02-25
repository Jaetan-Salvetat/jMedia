package fr.jaetan.jmedia.app.work_detail

import androidx.lifecycle.ViewModel
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.IWork
import org.mongodb.kbson.ObjectId

class WorkDetailViewModel(workType: WorkType?, workId: ObjectId): ViewModel() {
    lateinit var work: IWork
        private set
    var contentEdited: Boolean = false

    init {
        if ( workType.isNotNull() ) {
            MainViewModel.getController(workType!!).localWorks.find { it.id == workId }?.let {
                work = it
            }
        }
    }
}