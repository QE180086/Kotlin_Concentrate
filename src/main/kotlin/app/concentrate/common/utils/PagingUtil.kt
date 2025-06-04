package app.concentrate.common.utils

import app.concentrate.common.constant.SortMessageConstant
import app.concentrate.common.request.PagingRequest
import org.springframework.data.domain.Sort
import org.springframework.util.StringUtils

object PagingUtil {

    fun createSort(pagingRequest: PagingRequest): Sort {
        val sortRequest = pagingRequest.sortRequest
        return if (sortRequest != null && StringUtils.hasText(sortRequest.field)) {
            val direction = if (SortMessageConstant.SORT_DESC.equals(sortRequest.direction, ignoreCase = true))
                Sort.Direction.DESC else Sort.Direction.ASC
            Sort.by(direction, sortRequest.field)
        } else {
            Sort.unsorted()
        }
    }
}