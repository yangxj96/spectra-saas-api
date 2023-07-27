package io.github.yangxj96.server.reactive.demo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Id
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Represents a User entity in the database.
 *
 * This class defines the structure and properties of a User object, which corresponds to a row in the "t_user" table in the "db_user" schema.
 * It includes basic user information such as username, password, access status, creation and update information, and deletion status.
 * The class is annotated with the table name and schema information for mapping to the database.
 *
 * @property id The unique identifier for the user.
 * @property username The username of the user.
 * @property password The password of the user.
 * @property accessExpired Indicates whether the user's access has expired.
 * @property accessLocked Indicates whether the user's access is locked.
 * @property accessEnable Indicates whether the user's access is enabled.
 * @property credentialsExpired Indicates whether the user's credentials have expired.
 * @property createdBy The identifier of the user who created this user.
 * @property createdTime The creation time of the user record.
 * @property updatedBy The identifier of the user who last updated this user.
 * @property updatedTime The last update time of the user record.
 * @property deleted Indicates whether the user is deleted.
 */
@Table(name = "t_user", schema = "db_user")
@SQLDelete(sql = "UPDATE db_user.t_user SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
data class User(

    @Id
    val id: Long,

    /**
     * Variable representing the username.
     *
     * This variable stores the value of the username associated with a user.
     * It is used to uniquely identify a user in the system.
     *
     * @property username The username of the user.
     *
     * @since 1.0
     */
    @Column(name = "username")
    var username: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "access_expired")
    var accessExpired: Boolean,

    @Column(name = "access_locked")
    var accessLocked: Boolean,

    @Column(name = "access_enable")
    var accessEnable: Boolean,

    @Column(name = "credentials_expired")
    var credentialsExpired: Boolean,

    /**
     * 创建人
     */
    @Column(name = "created_by")
    var createdBy: Long,

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    var createdTime: LocalDateTime,

    /**
     * 更新人
     */
    @Column(name = "updated_by")
    var updatedBy: Long,

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    var updatedTime: LocalDateTime,

    /**
     * Indicates whether the entity has been deleted.
     *
     * @property deleted Boolean value indicating if the entity has been deleted
     */
    @JsonIgnore
    @Column(name = "deleted")
    var deleted: Boolean
)
