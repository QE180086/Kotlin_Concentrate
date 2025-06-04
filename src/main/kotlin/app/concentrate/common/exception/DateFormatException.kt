package app.concentrate.common.exception

class DateFormatException(
    val messageCode: String,
    message: String,
    cause: Throwable
) : RuntimeException(message, cause)
