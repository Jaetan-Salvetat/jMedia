package fr.jaetan.core.services

import android.content.Context
import android.util.Log
import androidx.navigation.NavHostController
import fr.jaetan.core.models.data.works.WorkType

// region Screens interfaces
interface IScreen {
    val route: String
}
interface ISampleScreen: IScreen {
    /**
     * @return The route of the screen
     */
    fun getNavRoute(): String = route
}
interface IScreenWithActivity {
    fun openActivity(context: Context)
}
interface IScreenWorkActivity {
    fun openActivity(context: Context, workType: WorkType)
}
interface IScreenWork: IScreen {
    val workName: String
    val workNameKey: String

    fun getNavRoute(name: String): String {
        Log.d("testt", workName)
        Log.d("testt", workNameKey)
        Log.d("testt", route)
        return "$workName/$name"
    }
}
// endregion


// region NavHostController extensions
/**
 * navigate to a screen and save the state of the current screen (if saveState is true)
 *
 * @property route The route of the screen
 */
fun NavHostController.push(route: String) {
    navigate(route) {
        popUpTo(route) {
            inclusive = true
        }
    }
}
// endregion