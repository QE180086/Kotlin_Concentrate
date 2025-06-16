package app.concentrate.projectmanagement.sessionmanagement.utils

import app.concentrate.projectmanagement.sessionmanagement.enumration.StatusSession
import app.concentrate.projectmanagement.sessionmanagement.repository.SessionRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class SessionSchedule(
    private val sessionRepository: SessionRepository
) {
    @Scheduled(fixedRate = 60000)
    fun updateExpiredSessions() {
        val now = Date()
        val sessions = sessionRepository.findByStatusAndEndTimeBefore(StatusSession.ACTIVE, now)

        sessions.forEach {
            it.status = StatusSession.ENDED
        }

        if (sessions.isNotEmpty()) {
            sessionRepository.saveAll(sessions)
            println("Updated ${sessions.size} expired sessions to END.")
        }
    }
}