package utils

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SessionData(val sessionId: String, val sessionStartedAtTime: Long)

class SessionManager(val userSettings: UserSettings) {
    private var contineSessionTimer: Job? = null
    private fun startContinueSessionTimer() {
        contineSessionTimer = MainScope().launch {
            while (true) {
                delay(MILLISECONDS_IN_MINUTE)
                continueSession()
            }
        }
    }

    private fun continueSession() {
        val session = getCurrentSession()
    }

    fun getCurrentSession(): SessionData? {
        val sessionId = userSettings.getString(SESSION_ID_KEY)
        val sessionStartedTime = userSettings.getLong(SESSION_STARTED_AT_KEY, 0)
        if (sessionId == null || sessionStartedTime <= 0) return null
        return SessionData(sessionId, sessionStartedTime)
    }

    companion object {
        const val MILLISECONDS_IN_MINUTE = 60000L
        const val SESSION_ID_KEY = "session_id"
        const val SESSION_STARTED_AT_KEY = "session_started_at"

    }
}
