package fr.jaetan.jmedia.works

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import fr.jaetan.core.models.data.WorkType
import fr.jaetan.jmedia.ui.theme.JMediaTheme

class WorkActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            JMediaTheme {
                Scaffold {
                    Text("Work Activity", modifier = Modifier.padding(it))
                }
            }
        }
    }

    companion object {
        fun launch(context: Context, workType: WorkType): Intent {
            val intent = Intent(context, WorkActivity::class.java)
            intent.putExtra("workType", workType)

            return intent
        }
    }
}