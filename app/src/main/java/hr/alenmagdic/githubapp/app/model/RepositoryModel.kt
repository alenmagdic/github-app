package hr.alenmagdic.githubapp.app.model

import java.util.*

data class RepositoryModel(
    val id: Int,
    val name: String,
    val description: String?,
    val programmingLanguage: String?,
    val dateCreated: Date,
    val dateUpdated: Date,
    val owner: UserModel,
    val watchersCount: Int,
    val starsCount: Int,
    val forksCount: Int,
    val issuesCount: Int,
    val htmlUrl: String
)