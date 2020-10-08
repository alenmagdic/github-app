package hr.alenmagdic.githubapp.app.contract

import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.app.model.UserModel

class RepositoryDetailsContract {
    interface PresenterInterface {
        var view: ViewInterface

        fun onViewRepositoryInBrowserClick(repository: RepositoryModel)
        fun onUserClick(user: UserModel)

        fun initialize(repositoryName: String, ownerName: String)
        fun destroy()
    }

    interface ViewInterface {
        fun showRepositoryDetails(repository: RepositoryModel)
        fun showRepositoryUnavailableMessage()
        fun showUserDetails(user: UserModel)
        fun showRepositoryDetailsOnWebsite(repositoryUrl: String)

        fun hideRepositoryDetails()
    }
}