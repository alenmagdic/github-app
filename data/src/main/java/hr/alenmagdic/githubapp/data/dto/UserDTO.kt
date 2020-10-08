package hr.alenmagdic.githubapp.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val username: String,

    @SerializedName("avatar_url")
    val logoUrl: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("public_repos")
    val publicRepositories: Int,

    @SerializedName("company")
    val company: String?,

    @SerializedName("location")
    val location: String?,

    @SerializedName("followers")
    val followersCount: Int,

    @SerializedName("following")
    val followingCount: Int,

    @SerializedName("created_at")
    val dateCreated: Date?,

    @SerializedName("updated_at")
    val dateUpdated: Date?,

    @SerializedName("html_url")
    val htmlUrl: String

)