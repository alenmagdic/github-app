package hr.alenmagdic.githubapp.data.dto.mapper

import hr.alenmagdic.githubapp.data.dto.RepositoryDTO
import hr.alenmagdic.githubapp.domain.model.Repository


class RepositoryMapper(private val userMapper: UserMapper) {

    fun toRepository(repositoryDTO: RepositoryDTO): Repository {
        return Repository(
            repositoryDTO.id,
            repositoryDTO.name,
            repositoryDTO.description,
            repositoryDTO.programmingLanguage,
            repositoryDTO.dateCreated,
            repositoryDTO.dateUpdated,
            userMapper.toUser(repositoryDTO.owner),
            repositoryDTO.watchersCount,
            repositoryDTO.starsCount,
            repositoryDTO.forksCount,
            repositoryDTO.issuesCount,
            repositoryDTO.htmlUrl
        )
    }
}