package fr.jaetan.jmedia.services

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.core.networking.GithubApi
import fr.jaetan.jmedia.core.networking.models.GithubApiEntities
import fr.jaetan.jmedia.extensions.log
import fr.jaetan.jmedia.models.ReplaceableLocal
import kotlinx.coroutines.launch
import ru.solrudev.ackpine.installer.PackageInstaller
import ru.solrudev.ackpine.installer.createSession
import ru.solrudev.ackpine.session.SessionResult
import ru.solrudev.ackpine.session.await
import ru.solrudev.ackpine.session.parameters.Confirmation
import ru.solrudev.ackpine.session.parameters.NotificationString
import ru.solrudev.ackpine.session.parameters.notification
import java.io.File

@Composable
private fun rememberGithubRelease(makeRequest: Boolean): GithubReleaseManager {
    val githubReleaseManager by remember { mutableStateOf(GithubReleaseManager()) }
    val scope = rememberCoroutineScope()

    if (!GlobalSettings.isInRelease || !makeRequest) return githubReleaseManager

    SideEffect {
        scope.launch {
            val tempRelease = GithubApi.getLastVersion()

            if (tempRelease.tagName != GlobalSettings.versionName) {
                githubReleaseManager.setRelease(tempRelease)
            }
        }
    }

    return githubReleaseManager
}

object LocalGithubReleaseManager: ReplaceableLocal<GithubReleaseManager>() {
    private var makeRequest = true

    @Composable
    override fun currentValue(): GithubReleaseManager {
        val settings =  rememberGithubRelease(makeRequest)
        makeRequest = false
        return settings
    }

}

class GithubReleaseManager {
    var release by mutableStateOf(null as GithubApiEntities.Release?)
        private set

    fun removeRelease() {
        release = null
    }

    @JvmName("addRelease")
    fun setRelease(r: GithubApiEntities.Release) {
        release = r
    }

    suspend fun download(context: Context) = runCatching {
        release?.let {
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val url = Uri.parse(it.assets.first().browserDownloadUrl)

            val request = DownloadManager.Request(url)
                .setTitle("${context.getString(R.string.app_name)} v${it.tagName}")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "jMedia-v${it.tagName}.apk")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                .setMimeType("application/vnd.android.package-archive")

            dm.enqueue(request)
        }
    }.onSuccess {
        val downloadPath = File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadPath, "jMedia-v${release?.tagName}.apk")
        installApk(context, file.toUri())
    }.onFailure {
        it.log()
    }

    private suspend fun installApk(context: Context, apkUri: Uri) {
        val packageInstaller = PackageInstaller.getInstance(context)
        val result = packageInstaller.createSession(apkUri) {
            confirmation = Confirmation.IMMEDIATE

            notification {
                title = NotificationString.default()
                icon = R.drawable.placeholder
            }
        }.await()

        try {
            when (result) {
                is SessionResult.Success -> Logger.d("Success")
                is SessionResult.Error -> Logger.d(result.cause.message)
            }
        } catch (e: Exception) {
            Logger.e(e)
        }
    }
}