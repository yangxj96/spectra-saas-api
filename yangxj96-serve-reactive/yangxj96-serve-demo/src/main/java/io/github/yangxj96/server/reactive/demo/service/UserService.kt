package io.github.yangxj96.server.reactive.demo.service

import io.github.yangxj96.server.reactive.demo.entity.User
import io.github.yangxj96.server.reactive.demo.repos.UserRepository
import jakarta.annotation.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService {

    @Resource
    lateinit var userRepository: UserRepository

    fun findByName(username: String): Mono<User> {
        return userRepository.findByName(username)
    }

    fun findAll(): Flux<User> {
        return userRepository.findAll()
    }

    fun save(user: User): Mono<User> {
        return userRepository.save(user)
    }

    fun findById(id: Long): Mono<User> {
        return userRepository.findById(id)
    }

    fun deleteById(id: Long): Mono<Void> {
        return userRepository.deleteById(id)
    }

}