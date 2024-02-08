package fr.jaetan.jmedia.services

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import fr.jaetan.jmedia.models.AnalyticsEvent

object Analytics {
    private val analytics = Firebase.analytics

    fun tagScreen(name: String) {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, name)
        }
    }

    fun tagEvent(event: AnalyticsEvent, vararg params: Pair<String, String>) {
        analytics.logEvent(event.name) {
            params.forEach {
                param(it.first, it.second)
            }
        }
    }
}