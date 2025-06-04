package app.concentrate.common.entity

import app.concentrate.common.constant.DataPatternConstant
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.time.LocalDateTime

@MappedSuperclass
//@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @CreatedBy
    @Column(updatable = false)
    var createdBy: String? = null

    @LastModifiedBy
    var updatedBy: String? = null

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(pattern = DataPatternConstant.DATE_TIME_FORMAT_MILLISECOND)
    var createdDate: LocalDateTime? = null

    @LastModifiedDate
    @JsonFormat(pattern = DataPatternConstant.DATE_TIME_FORMAT_MILLISECOND)
    var updatedDate: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        val now = LocalDateTime.now()
        createdDate = now
        updatedDate = now
    }

    @PreUpdate
    protected fun onUpdate() {
        updatedDate = LocalDateTime.now()
    }

    companion object {
        @JvmStatic
        private val serialVersionUID = 1L
    }
}
