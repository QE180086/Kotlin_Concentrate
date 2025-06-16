package app.concentrate.projectmanagement.sessionmanagement.mapper

import app.concentrate.projectmanagement.sessionmanagement.entity.PenaltyRule
import app.concentrate.projectmanagement.sessionmanagement.response.PenaltyRuleResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PenaltyRuleMapper {
    fun toDTO(penaltyRule: PenaltyRule): PenaltyRuleResponse
    fun toListDTO(listPenaltyRule: List<PenaltyRule>): List<PenaltyRuleResponse>
}