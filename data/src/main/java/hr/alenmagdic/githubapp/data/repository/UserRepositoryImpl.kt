package hr.alenmagdic.githubapp.data.repository

import hr.alenmagdic.githubapp.data.dataacces.dao.UserDAO
import hr.alenmagdic.githubapp.data.dto.mapper.UserMapper
import hr.alenmagdic.githubapp.domain.model.User
import hr.alenmagdic.githubapp.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDAO: UserDAO,
    private val userMapper: UserMapper
) : UserRepository {

    override fun findUser(username: String): User {
        val user = userDAO.findUser(username)
        return userMapper.toUser(user)
    }

}