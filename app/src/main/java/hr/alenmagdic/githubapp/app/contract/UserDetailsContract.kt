package hr.alenmagdic.githubapp.app.contract

import hr.alenmagdic.githubapp.app.model.UserModel

class UserDetailsContract {

    interface PresenterInterface {
        var view: ViewInterface

        fun onViewUserInBrowserClick(user: UserModel)

        fun initialize(username: String)
        fun destroy()
    }

    interface ViewInterface {
        fun showUserDetails(user: UserModel)
        fun showUserUnavailableMessage()
        fun showUserDetailsOnWebsite(userUrl: String)

        fun hideUserDetails()
    }
}