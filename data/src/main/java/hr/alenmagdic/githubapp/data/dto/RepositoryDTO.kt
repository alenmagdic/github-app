package hr.alenmagdic.githubapp.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class RepositoryDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("language")
    val programmingLanguage: String?,

    @SerializedName("created_at")
    val dateCreated: Date,

    @SerializedName("updated_at")
    val dateUpdated: Date,

    @SerializedName("owner")
    val owner: UserDTO,

    @SerializedName("watchers_count")
    val watchersCount: Int,

    @SerializedName("stargazers_count")
    val starsCount: Int,

    @SerializedName("forks_count")
    val forksCount: Int,

    @SerializedName("issues_count")
    val issuesCount: Int,

    @SerializedName("html_url")
    val htmlUrl: String

)