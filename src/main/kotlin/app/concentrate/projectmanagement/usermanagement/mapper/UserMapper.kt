package app.concentrate.projectmanagement.usermanagement.mapper

import app.concentrate.projectmanagement.usermanagement.entity.User
import app.concentrate.projectmanagement.usermanagement.response.UserResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UserMapper {

    @Mapping(target = "role", source = "role.name")
    fun toUserDTO(user: User): UserResponse

    @Mapping(target = "role", source = "role.name")
    fun toListUserDTO(users: List<User>): List<UserResponse>
}