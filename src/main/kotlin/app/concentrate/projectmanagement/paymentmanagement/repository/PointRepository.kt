package app.concentrate.projectmanagement.paymentmanagement.repository

import app.concentrate.projectmanagement.paymentmanagement.entity.Point
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PointRepository :JpaRepository<Point, String>{
    fun findByUserId(userId: String): Point?
    @Query("SELECT SUM(p.point) FROM Point p")
    fun getTotalPoints(): Int?
}