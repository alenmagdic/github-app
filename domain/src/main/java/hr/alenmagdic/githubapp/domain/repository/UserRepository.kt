package hr.alenmagdic.githubapp.domain.repository

import hr.alenmagdic.githubapp.domain.model.User

interface UserRepository {
    fun findUser(username: String): User
}