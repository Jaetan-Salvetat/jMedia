package fr.jaetan.jmedia.app

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import fr.jaetan.jmedia.services.MainViewModel
import fr.jaetan.jmedia.ui.theme.JMediaTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        requestNotificationPermission()
        requestStoragePermission()

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

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
            } else {
                if (!Environment.isExternalStorageManager()) {
                    val uri = Uri.fromParts("package", this.packageName, null)
                    val intent = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        data = uri
                    }

                    startActivity(intent)
                }
            }
        }
    }
}
