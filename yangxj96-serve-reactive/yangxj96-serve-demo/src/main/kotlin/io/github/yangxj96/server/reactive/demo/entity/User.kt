package io.github.yangxj96.server.reactive.demo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "t_user", schema = "db_user")
data class User(

    @Id
    private val id: Long,

    @Column(name = "username")
    private var username: String,

    @Column(name = "password")
    private var password: String,

    @Column(name = "access_expired")
    private var accessExpired: Boolean,

    @Column(name = "access_locked")
    private var accessLocked: Boolean,

    @Column(name = "access_enable")
    private var accessEnable: Boolean,

    @Column(name = "credentials_expired")
    private var credentialsExpired: Boolean,

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private var createdBy: Long,

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private var createdTime: LocalDateTime,

    /**
     * 更新人
     */
    @Column(name = "updated_by")
    private var updatedBy: Long,

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private var updatedTime: LocalDateTime,

    @Column(name = "deleted")
    private var deleted: Boolean
)
