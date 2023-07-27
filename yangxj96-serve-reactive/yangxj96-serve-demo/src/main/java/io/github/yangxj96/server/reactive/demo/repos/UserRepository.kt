package io.github.yangxj96.server.reactive.demo.repos

import io.github.yangxj96.server.reactive.demo.entity.User
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, Long> {


    /**
     * 自己写的SQL记得要手动加上deleted的过滤条件
     */
    @Query("SELECT * FROM db_user.t_user WHERE username = :username AND deleted = false LIMIT 1")
    fun findByName(username: String): Mono<User>

}