package hr.alenmagdic.githubapp.app.presenter

import hr.alenmagdic.githubapp.app.contract.RepositoryDetailsContract
import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.app.model.UserModel
import hr.alenmagdic.githubapp.app.model.mapper.RepositoryModelMapper
import hr.alenmagdic.githubapp.domain.model.Repository
import hr.alenmagdic.githubapp.domain.usecase.GetRepositoryDetailsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RepositoryDetailsPresenter(
    private val getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase,
    private val repositoryModelMapper: RepositoryModelMapper
) : RepositoryDetailsContract.PresenterInterface {

    override lateinit var view: RepositoryDetailsContract.ViewInterface
    private val disposables = CompositeDisposable()

    override fun initialize(repositoryName: String, ownerName: String) {
        view.hideRepositoryDetails()
        loadRepository(repositoryName, ownerName)
    }

    private fun loadRepository(repositoryName: String, ownerName: String) {
        getRepositoryDetailsUseCase.execute(repositoryName, ownerName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableSingleObserver<Repository>() {
                    override fun onSuccess(repository: Repository) {
                        val repositoryModel = repositoryModelMapper.toRepositoryModel(repository)
                        view.showRepositoryDetails(repositoryModel)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.showRepositoryUnavailableMessage()
                    }
                }
            ).also { disposables.add(it) }
    }

    override fun onUserClick(user: UserModel) {
        view.showUserDetails(user)
    }

    override fun onViewRepositoryInBrowserClick(repository: RepositoryModel) {
        view.showRepositoryDetailsOnWebsite(repository.htmlUrl)
    }

    override fun destroy() {
        disposables.dispose()
    }
}