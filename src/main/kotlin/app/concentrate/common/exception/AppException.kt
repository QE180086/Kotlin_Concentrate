package app.concentrate.common.exception

class AppException(
    val messageCode: String,
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)