package app.concentrate.common.request


import jakarta.validation.constraints.NotNull

data class SortRequest(
    @field:NotNull
    val direction: String,

    @field:NotNull
    val field: String
)