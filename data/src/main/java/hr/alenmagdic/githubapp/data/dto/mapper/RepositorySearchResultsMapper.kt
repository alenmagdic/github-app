package hr.alenmagdic.githubapp.data.dto.mapper

import hr.alenmagdic.githubapp.data.dto.RepositorySearchResultsDTO
import hr.alenmagdic.githubapp.domain.model.Repository
import hr.alenmagdic.githubapp.domain.model.SearchResults

class RepositorySearchResultsMapper(private val repositoryMapper: RepositoryMapper) {

    fun toRepositorySearch(repositorySearchResultsDTO: RepositorySearchResultsDTO): SearchResults<Repository> {
        return SearchResults(
            repositorySearchResultsDTO.totalCount,
            repositorySearchResultsDTO.repositories.map { repositoryMapper.toRepository(it) }
        )
    }
}