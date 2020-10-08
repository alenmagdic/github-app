package hr.alenmagdic.githubapp.domain.model

data class SearchResults<T>(
    val totalCount: Int,
    val items: List<T>
)