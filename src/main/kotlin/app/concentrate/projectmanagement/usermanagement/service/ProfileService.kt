package app.concentrate.projectmanagement.usermanagement.service

import app.concentrate.projectmanagement.usermanagement.request.CreateProfileDefaultRequest
import app.concentrate.projectmanagement.usermanagement.request.UpdateProfileRequest
import app.concentrate.projectmanagement.usermanagement.response.ProfileResponse

interface ProfileService {
    fun createDefaultProfile(request: CreateProfileDefaultRequest)

    /**
     * Update profile API
     *
     * @param request
     * @return
     */
    fun updateProfile(request: UpdateProfileRequest): ProfileResponse

    /**
     * Get profile detail by userId
     *
     * @param userId
     * @return
     */
    fun getDetailProfileByUserId(): ProfileResponse

    /**
     * Get profile detail by profileId
     *
     * @param profileId
     * @return
     */
    fun getDetailProfileByProfileId(profileId: String): ProfileResponse
}