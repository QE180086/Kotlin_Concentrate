package app.concentrate.projectmanagement.paymentmanagement.service

interface PointService {
    /**
     * Retrieves the points of a user.
     *
     * @param userId The ID of the user whose points are to be retrieved.
     * @return The points of the user.
     */
    fun getPoints(): Int

    /**
     * Adds points to a user's account.
     *
     * @param points The number of points to add.
     */
    fun addPoints(points: Int)

    /**
     * Minus points to a user's account.
     *
     * @param points The number of points to add.
     */
    fun minusPoints(points: Int)

    /**
     * Total points to a user's account.
     */
    fun totalPoints(): Int

}