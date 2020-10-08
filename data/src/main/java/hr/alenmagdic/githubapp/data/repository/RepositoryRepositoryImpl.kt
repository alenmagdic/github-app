package hr.alenmagdic.githubapp.data.repository

import hr.alenmagdic.githubapp.data.dataacces.dao.RepositoryDAO
import hr.alenmagdic.githubapp.data.dto.mapper.RepositoryMapper
import hr.alenmagdic.githubapp.data.dto.mapper.RepositorySearchResultsMapper
import hr.alenmagdic.githubapp.domain.SortOrder
import hr.alenmagdic.githubapp.domain.model.Repository
import hr.alenmagdic.githubapp.domain.model.SearchResults
import hr.alenmagdic.githubapp.domain.repository.RepositoryRepository

class RepositoryRepositoryImpl(
    private val repositoryDAO: RepositoryDAO,
    private val repositorySearchResultsMapper: RepositorySearchResultsMapper,
    private val repositoryMapper: RepositoryMapper
) : RepositoryRepository {

    override fun searchRepositories(
        query: String,
        sort: Repository.Sort,
        order: SortOrder,
        page: Int,
        perPage: Int
    ): SearchResults<Repository> {
        val searchResults = repositoryDAO.searchRepositories(query, sort, order, page, perPage)
        return repositorySearchResultsMapper.toRepositorySearch(searchResults)
    }

    override fun findRepository(name: String, ownerName: String): Repository {
        val repository = repositoryDAO.findRepository(name, ownerName)
        return repositoryMapper.toRepository(repository)
    }
}