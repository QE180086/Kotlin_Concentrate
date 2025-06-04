package app.concentrate.common.utils

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.exception.DateFormatException
import org.slf4j.LoggerFactory
import org.springframework.util.StringUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {

    private val log = LoggerFactory.getLogger(DateTimeUtil::class.java)

    /**
     * Convert String To Date
     */
    @JvmStatic
    fun convertStringToDate(format: String, strDate: String?): Date? {
        if (StringUtils.hasText(strDate)) {
            val simpleDateFormat = SimpleDateFormat(format)
            return try {
                simpleDateFormat.parse(strDate)
            } catch (e: ParseException) {
                log.error("Exception when parse String to Date. Input {} to format {}", strDate, format)
                throw DateFormatException(
                    MessageCodeConstant.M025_FORMAT_FAIL,
                    "Format from string input = $strDate to date output = $format fail.",
                    e
                )
            }
        } else {
            return null
        }
    }

    /**
     * Convert Date To String
     */
    @JvmStatic
    fun convertDateToString(date: Date?, format: String, timeZone: TimeZone): String {
        if (date != null) {
            return try {
                val simpleDateFormat = SimpleDateFormat(format)
                simpleDateFormat.timeZone = timeZone
                simpleDateFormat.format(date)
            } catch (e: Exception) {
                log.error("Exception when parse Date to String. Input {} to format {}", date, format)
                throw DateFormatException(
                    MessageCodeConstant.M025_FORMAT_FAIL,
                    "Format from Date input = $date to String output = $format fail.",
                    e
                )
            }
        } else {
            return ""
        }
    }
}
