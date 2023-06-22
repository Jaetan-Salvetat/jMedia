package fr.jaetan.jmedia.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.services.ISampleScreen
import fr.jaetan.jmedia.search.views.SearchScreen
import fr.jaetan.jmedia.ui.theme.JMediaTheme

class SearchActivity: ComponentActivity() {
    private lateinit var workType: WorkType
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        workType = WorkType.getFromString(intent.getStringExtra(WorkType.key))!!
        
        setContent {
            val navController = rememberNavController()

            JMediaTheme {
                NavHost(navController, SearchNavigator.search.route){
                    composable(SearchNavigator.search.route) {
                        SearchScreen(this@SearchActivity, workType).GetView()
                    }
                }
            }
        }
    }

    companion object {
        fun launch(context: Context, workType: WorkType): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(WorkType.key, workType.name)
            return intent
        }
    }
}

object SearchNavigator {
    val search = object: ISampleScreen {
        override val route = "search"
    }
}