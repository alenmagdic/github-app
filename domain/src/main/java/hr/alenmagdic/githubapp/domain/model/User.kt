package hr.alenmagdic.githubapp.domain.model

import java.util.*

data class User(
    val id: Int,
    val username: String,
    val logoUrl: String,
    val type: String,
    val name: String?,
    val publicRepositories: Int,
    val company: String?,
    val location: String?,
    val followersCount: Int,
    val followingCount: Int,
    val dateCreated: Date?,
    val dateUpdated: Date?,
    val htmlUrl: String
)