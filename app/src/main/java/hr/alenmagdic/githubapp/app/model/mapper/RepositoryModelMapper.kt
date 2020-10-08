package hr.alenmagdic.githubapp.app.model.mapper

import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.domain.model.Repository
import javax.inject.Inject

class RepositoryModelMapper @Inject constructor(private val userModelMapper: UserModelMapper) {

    fun toRepositoryModel(repository: Repository): RepositoryModel {
        return RepositoryModel(
            repository.id,
            repository.name,
            repository.description,
            repository.programmingLanguage,
            repository.dateCreated,
            repository.dateUpdated,
            userModelMapper.toUserModel(repository.owner),
            repository.watchersCount,
            repository.starsCount,
            repository.forksCount,
            repository.issuesCount,
            repository.htmlUrl
        )
    }

}