package hr.alenmagdic.githubapp.data.dto.mapper

import hr.alenmagdic.githubapp.data.dto.UserDTO
import hr.alenmagdic.githubapp.domain.model.User

class UserMapper {

    fun toUser(userDTO: UserDTO): User {
        return User(
            userDTO.id,
            userDTO.username,
            userDTO.logoUrl,
            userDTO.type,
            userDTO.name,
            userDTO.publicRepositories,
            userDTO.company,
            userDTO.location,
            userDTO.followersCount,
            userDTO.followingCount,
            userDTO.dateCreated,
            userDTO.dateUpdated,
            userDTO.htmlUrl
        )
    }
}