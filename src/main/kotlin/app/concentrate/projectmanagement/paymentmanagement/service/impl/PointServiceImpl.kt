package app.concentrate.projectmanagement.paymentmanagement.service.impl

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.exception.AppException
import app.concentrate.projectmanagement.paymentmanagement.constant.PointConstant
import app.concentrate.projectmanagement.paymentmanagement.repository.PointRepository
import app.concentrate.projectmanagement.paymentmanagement.service.PointService
import app.concentrate.projectmanagement.usermanagement.constant.UserConstant
import app.concentrate.projectmanagement.usermanagement.service.UserUtilService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class PointServiceImpl(
    private val pointRepository: PointRepository,
    private val userUserUtilService: UserUtilService,
) : PointService {
    override fun getPoints(): Int {
        val userId = userUserUtilService.getIdCurrentUser()
            ?: throw AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND)

        val point = pointRepository.findByUserId(userId)
            ?: throw AppException(MessageCodeConstant.M026_FAIL, PointConstant.POINT_IS_NOT_EXIST)

        return point.point
    }


    override fun addPoints(points: Int) {
        val userId = userUserUtilService.getIdCurrentUser()
            ?: throw AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND)
        val point = pointRepository.findByUserId(userId)
            ?: throw AppException(MessageCodeConstant.M026_FAIL, PointConstant.POINT_IS_NOT_EXIST)
        point.point += points
        pointRepository.save(point)
    }

    override fun minusPoints(points: Int) {
        val userId = userUserUtilService.getIdCurrentUser()
            ?: throw AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND)
        val point = pointRepository.findByUserId(userId)
            ?: throw AppException(MessageCodeConstant.M026_FAIL, PointConstant.POINT_IS_NOT_EXIST)
        if (point.point < points) {
            point.point = 0
            pointRepository.save(point)
        } else {
            point.point -= points
            pointRepository.save(point)
        }
    }

    override fun totalPoints(): Int {
        val totalPoints = pointRepository.getTotalPoints()
            ?: throw AppException(MessageCodeConstant.M026_FAIL, PointConstant.POINT_NOT_FOUND)
        return totalPoints
    }
}