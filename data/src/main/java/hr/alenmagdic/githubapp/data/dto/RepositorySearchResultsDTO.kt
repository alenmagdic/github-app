package hr.alenmagdic.githubapp.data.dto

import com.google.gson.annotations.SerializedName

data class RepositorySearchResultsDTO(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val repositories: List<RepositoryDTO>
)