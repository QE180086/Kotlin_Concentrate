package app.concentrate.common.dto

import app.concentrate.common.dto.MessageDTO

data class GenericResponse<T>(
    var isSuccess: Boolean = true,
    var message: MessageDTO? = null,
    var errors: List<ErrorDTO>? = null,
    var data: T? = null
)
