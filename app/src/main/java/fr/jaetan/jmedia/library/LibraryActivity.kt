package fr.jaetan.jmedia.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.jaetan.core.models.data.WorkType
import fr.jaetan.core.services.ISampleScreen
import fr.jaetan.core.services.IScreenWork
import fr.jaetan.jmedia.library.views.LibraryScreen
import fr.jaetan.jmedia.search.SearchActivity
import fr.jaetan.jmedia.ui.theme.JMediaTheme

class LibraryActivity: ComponentActivity() {
    private lateinit var workType: WorkType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        workType = WorkType.getFromString(intent.getStringExtra(WorkType.key))!!

        setContent {
            val navController = rememberNavController()

            JMediaTheme {
                NavHost(navController, LibraryNavigator.library.route){
                    composable(LibraryNavigator.library.route) {
                        LibraryScreen(this@LibraryActivity, workType).GetView()
                    }
                }
            }
        }
    }

    companion object {
        fun launch(context: Context, workType: WorkType): Intent {
            val intent = Intent(context, LibraryActivity::class.java)
            intent.putExtra(WorkType.key, workType.name)

            return intent
        }
    }
}

object LibraryNavigator {
    val library = object: ISampleScreen {
        override val route = "library"
    }

    val search = object: IScreenWork {
        override fun openActivity(context: Context, workType: WorkType) {
            context.startActivity(SearchActivity.launch(context, workType))
        }

    }
}