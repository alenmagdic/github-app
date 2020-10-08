package hr.alenmagdic.githubapp.app.contract

import androidx.paging.PagedList
import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.app.model.UserModel
import hr.alenmagdic.githubapp.domain.SortOrder
import hr.alenmagdic.githubapp.domain.model.Repository

class RepositorySearchContract {
    interface PresenterInterface {
        var view: ViewInterface

        fun onSubmitSearchQuery(query: String, sort: Repository.Sort, sortOrder: SortOrder)
        fun onSearchOpen()
        fun onSearchClose()
        fun onChangeSort(sort: Repository.Sort, sortOrder: SortOrder, query: String)
        fun onUserClick(userModel: UserModel)
        fun onRepositoryClick(repository: RepositoryModel)

        fun initialize()
        fun destroy()
    }

    interface ViewInterface {
        fun showInitialContent()
        fun showEnterQueryMessage()
        fun showSearchResults(repositories: PagedList<RepositoryModel?>)
        fun showSearchResultsTotalCount(totalCount: Int)
        fun showNoResults()
        fun showDataLoadFailedMessage()
        fun showRepositoryDetails(repository: RepositoryModel)
        fun showUserDetails(user: UserModel)
        fun showLoading()

        fun hideInitialContent()
        fun hideEnterQueryMessage()
        fun hideSearchResults()
        fun hideNoResults()
        fun hideLoading()
    }
}