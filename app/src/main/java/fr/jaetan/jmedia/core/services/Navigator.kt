package fr.jaetan.jmedia.core.services

object Navigator {
    val library = object: ISampleScreen {
        override val route = "library"
    }

    val search = object: ISampleScreen {
        override val route = "search"
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