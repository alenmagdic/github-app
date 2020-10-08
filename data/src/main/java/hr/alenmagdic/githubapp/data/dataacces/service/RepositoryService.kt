package hr.alenmagdic.githubapp.data.dataacces.service

import hr.alenmagdic.githubapp.data.dto.RepositoryDTO
import hr.alenmagdic.githubapp.data.dto.RepositorySearchResultsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryService {

    @GET("/search/repositories")
    fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): retrofit2.Call<RepositorySearchResultsDTO>

    @GET("/repos/{ownerName}/{repositoryName}")
    fun findRepository(
        @Path("repositoryName") repositoryName: String,
        @Path("ownerName") ownerName: String
    ): retrofit2.Call<RepositoryDTO>
}