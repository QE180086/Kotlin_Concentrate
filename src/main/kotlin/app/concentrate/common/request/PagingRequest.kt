package app.concentrate.common.request


import jakarta.validation.constraints.Min

data class PagingRequest(
    @field:Min(0)
    val page: Int,

    @field:Min(1)
    val size: Int,

    val sortRequest: SortRequest? = null
) {
    // Nếu muốn tạo thêm constructor chỉ có page và size
    constructor(page: Int, size: Int) : this(page, size, null)
}