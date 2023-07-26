package io.github.yangxj96.server.reactive.demo.repos

import io.github.yangxj96.server.reactive.demo.entity.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository


interface UserRepository:ReactiveCrudRepository<User,Long>