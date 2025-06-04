package app.concentrate.projectmanagementservice.configuration.handler

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.dto.ErrorDTO
import app.concentrate.common.dto.GenericResponse
import app.concentrate.common.dto.MessageDTO
import app.concentrate.common.exception.AppException
import jakarta.servlet.http.HttpServletRequest
import org.hibernate.PropertyValueException
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomerExceptionHandler : ResponseEntityExceptionHandler() {
    private val logger = LoggerFactory.getLogger(CustomerExceptionHandler::class.java)
    private val MESSAGE_ERROR = "Exception:\n MessageCode = %s \n Message = %s"

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors

        return if (errors.isNotEmpty()) {
            val firstError = errors[0]
            val errorDTO = ErrorDTO(
                firstError.field,
                firstError.defaultMessage ?: "Validation error"
            )

            val response: GenericResponse<Any> = GenericResponse(
                isSuccess = false,
                message = MessageDTO(
                    messageCode = MessageCodeConstant.M016_VALIDATION_FAILED,
                    messageDetail = errorDTO.message
                )
            )

            logger.error("${MessageCodeConstant.M016_VALIDATION_FAILED} : $errors")
            ResponseEntity(response, HttpStatus.BAD_REQUEST)
        } else {
            val defaultResponse: GenericResponse<Any> = GenericResponse(
                isSuccess = false,
                message = MessageDTO(
                    messageCode = MessageCodeConstant.M016_VALIDATION_FAILED,
                    messageDetail = "Validation failed"
                )
            )

            ResponseEntity(defaultResponse, HttpStatus.BAD_REQUEST)
        }
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleGlobalException(ex: Exception, request: HttpServletRequest): GenericResponse<Any?> {
        logger.error("Global Exception: ${ex.message}", ex)
        return GenericResponse(
            isSuccess = false,
            message = MessageDTO(
                messageCode = MessageCodeConstant.M026_FAIL,
                messageDetail = ex.message!!
            )
        )
    }

    @ExceptionHandler(AppException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun appException(ex: AppException, request: HttpServletRequest): GenericResponse<Any?> {
        logger.error(String.format(MESSAGE_ERROR, ex.messageCode, ex.cause))
        return GenericResponse(
            isSuccess = false,
            message = MessageDTO(
                messageCode = ex.messageCode,
                messageDetail = ex.message!!
            )
        )
    }
//
//    @ExceptionHandler(ExportException::class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    fun exportException(ex: ExportException, request: HttpServletRequest): GenericResponse<Any?> {
//        logger.error(String.format(MESSAGE_ERROR, ex.messageCode, ex.cause))
//        return GenericResponse(
//            isSuccess = false,
//            message = MessageDTO(
//                messageCode = ex.messageCode,
//                messageDetail = ex.message
//            )
//        )
//    }

    @ExceptionHandler(NullPointerException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNullPointerException(ex: NullPointerException, request: HttpServletRequest): GenericResponse<Any?> {
        logger.error("NullPointerException: ${ex.message}", ex)
        return GenericResponse(
            isSuccess = false,
            message = MessageDTO(
                messageCode = "Null Pointer Exception",
                messageDetail = ex.message!!
            )
        )
    }

    @ExceptionHandler(PropertyValueException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePropertyValueException(ex: PropertyValueException, request: HttpServletRequest): GenericResponse<Any?> {
        logger.error("Property Value Exception: ${ex.message}", ex)
        return GenericResponse(
            isSuccess = false,
            message = MessageDTO(
                messageCode = "Property Value Exception",
                messageDetail = ex.message!!
            )
        )
    }
}
