package hr.alenmagdic.githubapp.app.presenter

import hr.alenmagdic.githubapp.app.contract.UserDetailsContract
import hr.alenmagdic.githubapp.app.model.UserModel
import hr.alenmagdic.githubapp.app.model.mapper.UserModelMapper
import hr.alenmagdic.githubapp.domain.model.User
import hr.alenmagdic.githubapp.domain.usecase.GetUserDetailsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserDetailsPresenter(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val userModelMapper: UserModelMapper
) : UserDetailsContract.PresenterInterface {

    override lateinit var view: UserDetailsContract.ViewInterface
    private val disposables = CompositeDisposable()

    override fun initialize(username: String) {
        view.hideUserDetails()
        getUser(username)
    }

    private fun getUser(username: String) {
        getUserDetailsUseCase.execute(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableSingleObserver<User>() {
                    override fun onSuccess(user: User) {
                        val userModel = userModelMapper.toUserModel(user)
                        view.showUserDetails(userModel)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.showUserUnavailableMessage()
                    }
                }
            ).also { disposables.add(it) }
    }

    override fun onViewUserInBrowserClick(user: UserModel) {
        view.showUserDetailsOnWebsite(user.htmlUrl)
    }

    override fun destroy() {
        disposables.dispose()
    }
}