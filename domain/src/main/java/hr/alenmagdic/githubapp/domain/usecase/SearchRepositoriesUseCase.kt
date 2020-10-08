package hr.alenmagdic.githubapp.domain.usecase

import hr.alenmagdic.githubapp.domain.SortOrder
import hr.alenmagdic.githubapp.domain.model.Repository
import hr.alenmagdic.githubapp.domain.model.SearchResults
import hr.alenmagdic.githubapp.domain.repository.RepositoryRepository

class SearchRepositoriesUseCase(private val repository: RepositoryRepository) {

    fun execute(
        query: String,
        sort: Repository.Sort,
        order: SortOrder,
        page: Int,
        perPage: Int
    ): SearchResults<Repository> {
        return repository.searchRepositories(query, sort, order, page, perPage)
    }
}