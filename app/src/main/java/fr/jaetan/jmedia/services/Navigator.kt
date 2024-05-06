package fr.jaetan.jmedia.services

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.navigation.NavBackStackEntry

object Navigator {
    val library = object : ISampleScreen {
        override val route = "library"
    }
    val settings = object : ISampleScreen {
        override val route = "settings"
    }
    val appearance = object : ISampleScreen {
        override val route = "appearance"
    }
    val search = object : IScreen {
        val searchValue = "searchValue"
        override val route = "search/{$searchValue}"

        fun getNavRoute(q: String): String {
            return "search/$q"
        }
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

// Animations
fun AnimatedContentTransitionScope<NavBackStackEntry>.slideIntoVerticalContainer() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Up,
    spring(.85f, Spring.StiffnessLow)
)
fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutVerticalContainer() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Down,
    spring(.85f, Spring.StiffnessLow)
)
fun AnimatedContentTransitionScope<NavBackStackEntry>.slideIntoHorizontalContainer() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Left,
    spring(.85f, Spring.StiffnessLow)
)
fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutHorizontalContainer() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Right,
    spring(.85f, Spring.StiffnessLow)
)