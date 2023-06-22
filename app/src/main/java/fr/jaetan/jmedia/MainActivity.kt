package fr.jaetan.jmedia

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.services.ISampleScreen
import fr.jaetan.core.services.IScreenWork
import fr.jaetan.core.services.MainViewModel
import fr.jaetan.jmedia.home.views.HomeScreen
import fr.jaetan.jmedia.library.LibraryActivity
import fr.jaetan.jmedia.ui.theme.JMediaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        MainViewModel.initialize(this)

        setContent {
            val navController = rememberNavController()

            JMediaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController, HomeNavigator.home.route) {
                        composable(HomeNavigator.home.route) {
                            HomeScreen().GetView(navController)
                        }
                    }
                }
            }
        }
    }
}

object HomeNavigator {
    val home = object: ISampleScreen {
        override val route = "home"
    }

    val work = object: IScreenWork {
        override fun openActivity(context: Context, workType: WorkType) {
            context.startActivity(LibraryActivity.launch(context, workType))
        }

    }
}
