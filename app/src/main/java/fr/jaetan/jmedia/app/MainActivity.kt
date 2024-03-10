package fr.jaetan.jmedia.app

import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import fr.jaetan.jmedia.services.GlobalSettings
import fr.jaetan.jmedia.services.MainViewModel
import fr.jaetan.jmedia.ui.theme.JMediaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        requestNotificationPermission()

        lifecycleScope.launch {
            MainViewModel.initialize(this@MainActivity)
        }

        setContent {
            val navController = rememberNavController()

            JMediaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(navController)
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(POST_NOTIFICATIONS)
        }
    }

    fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && GlobalSettings.isInRelease) {
            requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
        }
    }
}
