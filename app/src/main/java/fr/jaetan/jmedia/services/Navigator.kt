package fr.jaetan.jmedia.services

object Navigator {
    val home = object: ISampleScreen {
        override val route = "home"
    }
    val appearance = object: ISampleScreen {
        override val route = "appearance"
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