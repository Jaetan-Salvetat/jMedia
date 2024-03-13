package fr.jaetan.jmedia.app

import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
                App(navController)
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && GlobalSettings.isInDemo) {
            requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
        }
    }
}