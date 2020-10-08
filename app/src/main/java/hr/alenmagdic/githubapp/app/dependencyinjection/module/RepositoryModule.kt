package hr.alenmagdic.githubapp.app.dependencyinjection.module

import dagger.Module
import dagger.Provides
import hr.alenmagdic.githubapp.app.contract.RepositoryDetailsContract
import hr.alenmagdic.githubapp.app.contract.RepositorySearchContract
import hr.alenmagdic.githubapp.app.dependencyinjection.ActivityScope
import hr.alenmagdic.githubapp.app.model.mapper.RepositoryModelMapper
import hr.alenmagdic.githubapp.app.presenter.RepositoryDetailsPresenter
import hr.alenmagdic.githubapp.app.presenter.RepositorySearchPresenter
import hr.alenmagdic.githubapp.domain.repository.RepositoryRepository
import hr.alenmagdic.githubapp.domain.usecase.GetRepositoryDetailsUseCase
import hr.alenmagdic.githubapp.domain.usecase.SearchRepositoriesUseCase

@Module
class RepositoryModule {

    @Provides
    @ActivityScope
    fun provideGetRepositoryDetailsUseCase(repositoryRepository: RepositoryRepository): GetRepositoryDetailsUseCase {
        return GetRepositoryDetailsUseCase(repositoryRepository)
    }

    @Provides
    @ActivityScope
    fun provideSearchRepositoriesUseCase(repositoryRepository: RepositoryRepository): SearchRepositoriesUseCase {
        return SearchRepositoriesUseCase(repositoryRepository)
    }

    @Provides
    @ActivityScope
    fun provideRepositoryDetailsPresenter(
        getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase,
        repositoryModelMapper: RepositoryModelMapper
    ): RepositoryDetailsContract.PresenterInterface {

        return RepositoryDetailsPresenter(getRepositoryDetailsUseCase, repositoryModelMapper)
    }

    @Provides
    @ActivityScope
    fun provideRepositorySearchPresenter(
        searchRepositoriesUseCase: SearchRepositoriesUseCase,
        repositoryModelMapper: RepositoryModelMapper
    ): RepositorySearchContract.PresenterInterface {

        return RepositorySearchPresenter(searchRepositoriesUseCase, repositoryModelMapper)
    }
}