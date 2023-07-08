package fr.jaetan.jmedia.work_detail.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.jaetan.core.extensions.isNull
import fr.jaetan.core.models.data.works.WorkType
import fr.jaetan.core.models.ui.Screen
import fr.jaetan.jmedia.work_detail.WorkDetailViewModel

class WorkDetailScreen(workType: WorkType, workName: String): Screen<WorkDetailViewModel>() {
    override val viewModel = WorkDetailViewModel(workType, workName)

    @Composable
    override fun Content() {
        ContentView()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun GetView(nc: NavHostController?) {
        initialize(nc)

        Scaffold { Box { Content() } }
    }

    override fun initialize(nc: NavHostController?) {
        super.initialize(nc)
        if (viewModel.work.isNull()) {
            viewModel.fetchManga()
        }
    }
}