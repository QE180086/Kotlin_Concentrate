package app.concentrate.projectmanagement.paymentmanagement.mapper

import app.concentrate.projectmanagement.paymentmanagement.entity.Point
import app.concentrate.projectmanagement.paymentmanagement.response.PointResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PointMapper {
    fun toDTO(point: Point): PointResponse
    fun toListDTO(points: List<Point>): List<PointResponse>
}