package app.concentrate.projectmanagement.usermanagement.controller

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.dto.GenericResponse
import app.concentrate.common.dto.MessageDTO
import com.cloudinary.Cloudinary
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@RestController
@RequestMapping("/api/upload")
class UploadController(
    private val cloudinary: Cloudinary
) {

    @PostMapping(consumes = ["multipart/form-data"])
    fun uploadImage(@RequestParam("file") file: MultipartFile):
            GenericResponse<Any> {
        return try {
            val uploadResult = cloudinary.uploader().upload(
                file.bytes,
                mapOf("resource_type" to "auto")
            )
            val url = uploadResult["secure_url"] as String
            GenericResponse(
                message = MessageDTO(
                    messageCode = MessageCodeConstant.M001_SUCCESS,
                    messageDetail = MessageConstant.SUCCESS
                ),
                isSuccess = true,
                data = mapOf("url" to url)
            )
        } catch (e: IOException) {
            GenericResponse(
                message = MessageDTO(
                    messageCode = MessageCodeConstant.M026_FAIL,
                    messageDetail = e.message ?: "Unknown error"
                ),
                isSuccess = false,
                data = null
            )
        }
    }
}