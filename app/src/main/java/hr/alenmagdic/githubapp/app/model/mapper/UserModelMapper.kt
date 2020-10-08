package hr.alenmagdic.githubapp.app.model.mapper

import hr.alenmagdic.githubapp.app.model.UserModel
import hr.alenmagdic.githubapp.domain.model.User
import javax.inject.Inject

class UserModelMapper @Inject constructor() {

    fun toUserModel(user: User): UserModel {
        return UserModel(
            user.id,
            user.username,
            user.logoUrl,
            user.type,
            user.name,
            user.publicRepositories,
            user.company,
            user.location,
            user.followersCount,
            user.followingCount,
            user.dateCreated,
            user.dateUpdated,
            user.htmlUrl
        )
    }

}