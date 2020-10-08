package hr.alenmagdic.githubapp.app.adapter.datasource

import androidx.paging.PageKeyedDataSource
import hr.alenmagdic.githubapp.app.model.RepositoryModel

class RepositoryPagedDataSource(private val dataProvider: PagedRepositoryDataProvider) :
    PageKeyedDataSource<Int, RepositoryModel>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, RepositoryModel>) {
        val repositories = dataProvider.getRepositories(
            FIRST_PAGE,
            PAGE_SIZE
        )
        callback.onResult(repositories, null, FIRST_PAGE.inc())
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryModel>) {
        val repositories = dataProvider.getRepositories(params.key,
            PAGE_SIZE
        )

        val previousPage = if (params.key == FIRST_PAGE) null else params.key.dec()
        callback.onResult(repositories, previousPage)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryModel>) {
        val repositories = dataProvider.getRepositories(params.key,
            PAGE_SIZE
        )

        val nextPage = if (repositories.size < PAGE_SIZE) null else params.key.inc()
        callback.onResult(repositories, nextPage)
    }

    interface PagedRepositoryDataProvider {
        fun getRepositories(page: Int, perPage: Int): List<RepositoryModel>
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 40
    }
}