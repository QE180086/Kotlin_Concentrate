package app.concentrate.projectmanagement.usermanagement.service.impl

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.exception.AppException
import app.concentrate.projectmanagement.usermanagement.constant.UserConstant
import app.concentrate.projectmanagement.usermanagement.entity.Profile
import app.concentrate.projectmanagement.usermanagement.enumration.Gender
import app.concentrate.projectmanagement.usermanagement.mapper.ProfileMapper
import app.concentrate.projectmanagement.usermanagement.repository.ProfileRepository
import app.concentrate.projectmanagement.usermanagement.repository.UserRepository
import app.concentrate.projectmanagement.usermanagement.request.CreateProfileDefaultRequest
import app.concentrate.projectmanagement.usermanagement.request.UpdateProfileRequest
import app.concentrate.projectmanagement.usermanagement.response.ProfileResponse
import app.concentrate.projectmanagement.usermanagement.service.ProfileService
import app.concentrate.projectmanagement.usermanagement.service.UserUtilService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository,
    private val userUtilService: UserUtilService,
    private val profileMapper: ProfileMapper
) : ProfileService {
    override fun createDefaultProfile(request: CreateProfileDefaultRequest) {
        val profile = Profile().apply {
            gender = Gender.MALE
            fullName = request.fullName
            user = request.user
            avatar = "https://dongvat.edu.vn/upload/2025/01/avatar-zenitsu-cute-10.webp"
        }
        profileRepository.save(profile)
    }

    override fun updateProfile(request: UpdateProfileRequest): ProfileResponse {
        val user = userUtilService.getIdCurrentUser()?.let {
            userRepository.findById(it)
                .orElseThrow {
                    AppException(MessageCodeConstant.M006_UNAUTHORIZED, UserConstant.USER_NOT_FOUND)
                }
        }
        val profile = user?.id?.let { profileRepository.findByUserId(it) }
            ?: throw AppException(MessageCodeConstant.M003_NOT_FOUND, MessageConstant.DATA_NOT_FOUND)

        updateProfilePerson(profile, request)

        return profileMapper.toResponse(profile)
    }

    override fun getDetailProfileByUserId(): ProfileResponse {
        val user = userUtilService.getIdCurrentUser()?.let {
            userRepository.findById(it)
                .orElseThrow {
                    AppException(MessageCodeConstant.M006_UNAUTHORIZED, UserConstant.USER_NOT_FOUND)
                }
        }
        val profile = user?.id?.let { profileRepository.findByUserId(it) }
            ?: throw AppException(MessageCodeConstant.M003_NOT_FOUND, MessageConstant.DATA_NOT_FOUND)
        return profileMapper.toResponse(profile)
    }

    override fun getDetailProfileByProfileId(profileId: String): ProfileResponse {
        val profile = profileRepository.findById(profileId)
            .orElseThrow {
                AppException(MessageCodeConstant.M003_NOT_FOUND, MessageConstant.DATA_NOT_FOUND)
            }

        return profileMapper.toResponse(profile)
    }


    private fun updateProfilePerson(profile: Profile, request: UpdateProfileRequest) {
        if (!request.avatar.isNullOrBlank()) {
            profile.avatar = request.avatar
        }
        if (!request.nickName.isNullOrBlank()) {
            profile.nickName = request.nickName
        }
        if (!request.fullName.isNullOrBlank()) {
            profile.fullName = request.fullName
        }
        request.gender?.let {
            profile.gender = it
        }
        request.dateOfBirth?.let {
            profile.dateOfBirth = it
        }

        profileRepository.save(profile)
    }

}