package fr.jaetan.jmedia

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import fr.jaetan.core.models.data.Manga
import fr.jaetan.core.models.data.WorkType
import fr.jaetan.core.services.ISampleScreen
import fr.jaetan.core.services.IScreenWork
import fr.jaetan.core.services.MainViewModel
import fr.jaetan.core.services.Scrapper
import fr.jaetan.jmedia.home.views.HomeScreen
import fr.jaetan.jmedia.ui.theme.JMediaTheme
import fr.jaetan.jmedia.library.LibraryActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

@Composable
fun Greeting() {
    val mangas = remember { mutableStateListOf<Manga>() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            mangas.clear()
            mangas.addAll(Scrapper.getMangas("oshi"))
        }
    }

    LazyColumn {
        items(mangas) {
            MangaItem(it)
        }
    }
}

@Composable
fun MangaItem(manga: Manga) {
    Log.d("testt", manga.cover)

    Column(Modifier.padding(vertical = 5.dp, horizontal = 5.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(
                model = manga.cover,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp),
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = null,
                        contentScale = contentScale
                    )
                }
            )

            Column(Modifier.padding(start = 10.dp)) {
                Text(manga.title)
                Text(manga.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }

        Divider(Modifier.padding(5.dp))
    }
}
