package fr.jaetan.jmedia.core.services

import fr.jaetan.jmedia.models.WorkType

object Navigator {

    val home = object: ISampleScreen {
        override val route = "home"
    }

    val search = object: ISampleScreen {
        override val route = "search"
    }

    val workDetail = object: IWorkDetail {
        override val workType: String = "workType"
        override val workId: String = "WorkId"


        override fun getNavRoute(workId: String, workType: WorkType) {
            TODO("Not yet implemented")
        }

        override val route: String
            get() = "work_detail/{$workType}/{$workId}"
    }
}

// Screens navigator models
interface IScreen {
    val route: String
}
interface ISampleScreen: IScreen {
    /**
     * @return The route of the screen
     */
    fun getNavRoute(): String = route
}

interface IWorkDetail: IScreen {
    val workType: String
    val workId: String
    fun getNavRoute(workId: String, workType: WorkType)
}