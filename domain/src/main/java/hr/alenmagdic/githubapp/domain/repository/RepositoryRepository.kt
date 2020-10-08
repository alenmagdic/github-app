package hr.alenmagdic.githubapp.domain.repository

import hr.alenmagdic.githubapp.domain.SortOrder
import hr.alenmagdic.githubapp.domain.model.Repository
import hr.alenmagdic.githubapp.domain.model.SearchResults

interface RepositoryRepository {
    fun searchRepositories(
        query: String,
        sort: Repository.Sort,
        order: SortOrder,
        page: Int,
        perPage: Int
    ): SearchResults<Repository>

    fun findRepository(name: String, ownerName: String): Repository
}