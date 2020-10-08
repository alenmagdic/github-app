package hr.alenmagdic.githubapp.data.dataacces.dao

import hr.alenmagdic.githubapp.data.dataacces.service.UserService
import hr.alenmagdic.githubapp.data.dto.UserDTO

class UserDAO(private val userService: UserService) : BaseDAO() {

    fun findUser(username: String): UserDTO {
        val serviceCall = userService.findUser(username)
        return getDataFromService(serviceCall)
    }
}