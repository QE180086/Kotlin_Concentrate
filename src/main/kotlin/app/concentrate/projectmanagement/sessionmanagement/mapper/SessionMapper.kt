package app.concentrate.projectmanagement.sessionmanagement.mapper

import app.concentrate.projectmanagement.sessionmanagement.entity.PenaltyRule
import app.concentrate.projectmanagement.sessionmanagement.entity.Session
import app.concentrate.projectmanagement.sessionmanagement.response.SessionPenaltyResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface SessionMapper {
    @Mapping(source = "session.user.username", target = "username")
    fun toDTO(session: Session) : SessionResponse

    fun toListDTO(listSession: List<Session>) : List<SessionResponse>

    @Mapping(source = "session.user.username", target = "username")
    fun toDTO(session: Session,penalties: List<PenaltyRule> ): SessionPenaltyResponse
}