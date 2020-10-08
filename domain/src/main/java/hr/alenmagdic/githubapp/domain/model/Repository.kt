package hr.alenmagdic.githubapp.domain.model

import java.util.*

data class Repository(
    val id: Int,
    val name: String,
    val description: String?,
    val programmingLanguage: String?,
    val dateCreated: Date,
    val dateUpdated: Date,
    val owner: User,
    val watchersCount: Int,
    val starsCount: Int,
    val forksCount: Int,
    val issuesCount: Int,
    val htmlUrl: String
) {
    enum class Sort {
        STARS, FORKS, UPDATED
    }
}