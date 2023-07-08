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
import fr.jaetan.core.services.IScreenWork
import fr.jaetan.jmedia.search.views.SearchScreen
import fr.jaetan.jmedia.ui.theme.JMediaTheme
import fr.jaetan.jmedia.work_detail.views.WorkDetailScreen

class SearchActivity: ComponentActivity() {
    private lateinit var workType: WorkType
    private lateinit var searchViewModel: SearchViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        workType = WorkType.getFromString(intent.getStringExtra(WorkType.key))!!
        searchViewModel = SearchViewModel(workType)
        
        setContent {
            val navController = rememberNavController()

            JMediaTheme {
                NavHost(navController, SearchNavigator.search.route){
                    composable(SearchNavigator.search.route) {
                        SearchScreen(this@SearchActivity, searchViewModel).GetView(navController)
                    }
                    composable(SearchNavigator.workDetail.route) {
                        val workName = it.arguments?.getString(SearchNavigator.workDetail.workNameKey)!!
                        WorkDetailScreen(workType, workName).GetView(navController)
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
    val workDetail = object: IScreenWork {
        override val workName = "work_detail"
        override val workNameKey = "work_name"
        override val route = "$workName/{$workNameKey}"
    }
}