package app.concentrate.projectmanagement.usermanagement.controller

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.dto.GenericResponse
import app.concentrate.common.dto.MessageDTO
import app.concentrate.projectmanagement.usermanagement.request.UpdateProfileRequest
import app.concentrate.projectmanagement.usermanagement.response.ProfileResponse
import app.concentrate.projectmanagement.usermanagement.service.ProfileService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val profileService: ProfileService
){
    @PutMapping
    fun updateProfile(@Valid @RequestBody request: UpdateProfileRequest): ResponseEntity<GenericResponse<ProfileResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = profileService.updateProfile(request)
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/user")
    fun getProfileByUserId(): ResponseEntity<GenericResponse<ProfileResponse>> {
        val profileResponse = profileService.getDetailProfileByUserId()

        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = profileResponse
        )

        return ResponseEntity.ok(response)
    }
    @GetMapping("/{profileId}")
    fun getProfileByProfileId(@PathVariable profileId: String): ResponseEntity<GenericResponse<ProfileResponse>> {
        val profileResponse = profileService.getDetailProfileByProfileId(profileId)

        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = profileResponse
        )

        return ResponseEntity.ok(response)
    }

}