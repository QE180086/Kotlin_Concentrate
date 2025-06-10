package app.concentrate.projectmanagement.usermanagement.mapper

import app.concentrate.projectmanagement.usermanagement.entity.Profile
import app.concentrate.projectmanagement.usermanagement.response.ProfileResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ProfileMapper {
    fun toResponse(profileEntity: Profile): ProfileResponse
}