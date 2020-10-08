package hr.alenmagdic.githubapp.app.presenter

import androidx.paging.DataSource
import androidx.paging.RxPagedListBuilder
import hr.alenmagdic.githubapp.app.adapter.datasource.RepositoryPagedDataSource
import hr.alenmagdic.githubapp.app.contract.RepositorySearchContract
import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.app.model.UserModel
import hr.alenmagdic.githubapp.app.model.mapper.RepositoryModelMapper
import hr.alenmagdic.githubapp.data.dataacces.DataAccessException
import hr.alenmagdic.githubapp.domain.SortOrder
import hr.alenmagdic.githubapp.domain.model.Repository
import hr.alenmagdic.githubapp.domain.usecase.SearchRepositoriesUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class RepositorySearchPresenter(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
    private val repositoryModelMapper: RepositoryModelMapper
) : RepositorySearchContract.PresenterInterface {

    override lateinit var view: RepositorySearchContract.ViewInterface
    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun onSubmitSearchQuery(query: String, sort: Repository.Sort, sortOrder: SortOrder) {
        view.hideEnterQueryMessage()
        view.showLoading()

        val dataProvider = object : RepositoryPagedDataSource.PagedRepositoryDataProvider {
            override fun getRepositories(page: Int, perPage: Int): List<RepositoryModel> {
                val searchResults = try {
                    searchRepositoriesUseCase.execute(query, sort, sortOrder, page, perPage)
                } catch (ex: DataAccessException) {
                    null
                }

                Completable.fromAction {
                    if (searchResults != null) {
                        if (searchResults.totalCount > 0) {
                            view.showSearchResultsTotalCount(searchResults.totalCount)
                        }
                    } else {
                        view.hideLoading()
                        view.showDataLoadFailedMessage()
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                    .also { disposables.add(it) }

                return searchResults?.items?.map { repositoryModelMapper.toRepositoryModel(it) } ?: listOf()
            }
        }

        val dataSourceFactory = object : DataSource.Factory<Int, RepositoryModel?>() {
            override fun create(): DataSource<Int, RepositoryModel?> =
                RepositoryPagedDataSource(dataProvider)
        }

        RxPagedListBuilder(dataSourceFactory, RepositoryPagedDataSource.PAGE_SIZE)
            .buildObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.hideLoading()
                view.hideSearchResults()
                view.hideNoResults()

                if (it.loadedCount == 0) {
                    view.showNoResults()
                } else {
                    view.showSearchResults(it)
                }
            }.also { disposables.add(it) }
    }

    override fun onChangeSort(sort: Repository.Sort, sortOrder: SortOrder, query: String) {
        onSubmitSearchQuery(query, sort, sortOrder)
    }

    override fun onSearchOpen() {
        view.hideInitialContent()
        view.showEnterQueryMessage()
    }

    override fun onSearchClose() {
        view.hideEnterQueryMessage()
        view.hideLoading()
        view.hideNoResults()
        view.hideSearchResults()
        view.showInitialContent()
    }

    override fun onRepositoryClick(repository: RepositoryModel) {
        view.showRepositoryDetails(repository)
    }

    override fun onUserClick(userModel: UserModel) {
        view.showUserDetails(userModel)
    }

    override fun initialize() {
        view.hideSearchResults()
        view.hideNoResults()
        view.hideLoading()
        view.hideEnterQueryMessage()
        view.showInitialContent()
    }

    override fun destroy() {
        disposables.dispose()
    }

}