package hr.alenmagdic.githubapp.data.dataacces.dao

import hr.alenmagdic.githubapp.data.dataacces.service.RepositoryService
import hr.alenmagdic.githubapp.data.dto.RepositoryDTO
import hr.alenmagdic.githubapp.data.dto.RepositorySearchResultsDTO
import hr.alenmagdic.githubapp.domain.SortOrder
import hr.alenmagdic.githubapp.domain.model.Repository

class RepositoryDAO(private val repositoryService: RepositoryService) : BaseDAO() {

    fun searchRepositories(
        query: String,
        sort: Repository.Sort,
        order: SortOrder,
        page: Int,
        perPage: Int
    ): RepositorySearchResultsDTO {
        val serviceCall = repositoryService
            .getRepositories(
                query,
                mapSortToServiceParameterValue(sort),
                mapSortOrderToServiceParameterValue(order),
                page,
                perPage
            )

        return getDataFromService(serviceCall)
    }

    private fun mapSortToServiceParameterValue(sort: Repository.Sort): String {
        return when (sort) {
            Repository.Sort.STARS -> "stars"
            Repository.Sort.FORKS -> "forks"
            Repository.Sort.UPDATED -> "updated"
        }
    }

    private fun mapSortOrderToServiceParameterValue(sort: SortOrder): String {
        return when (sort) {
            SortOrder.ASCENDING -> "asc"
            SortOrder.DESCENDING -> "desc"
        }
    }

    fun findRepository(repositoryName: String, ownerName: String): RepositoryDTO {
        val serviceCall = repositoryService
            .findRepository(
                repositoryName,
                ownerName
            )

        return getDataFromService(serviceCall)
    }
}