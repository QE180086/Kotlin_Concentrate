package app.concentrate.common.response

import app.concentrate.common.request.PagingRequest

data class PagingResponse<T>(
    val content: List<T>? = null,
    val request: PagingRequest? = null,
    val totalElement: Long = 0
)