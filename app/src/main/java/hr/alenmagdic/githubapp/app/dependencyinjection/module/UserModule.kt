package hr.alenmagdic.githubapp.app.dependencyinjection.module

import dagger.Module
import dagger.Provides
import hr.alenmagdic.githubapp.app.contract.UserDetailsContract
import hr.alenmagdic.githubapp.app.dependencyinjection.ActivityScope
import hr.alenmagdic.githubapp.app.model.mapper.UserModelMapper
import hr.alenmagdic.githubapp.app.presenter.UserDetailsPresenter
import hr.alenmagdic.githubapp.domain.repository.UserRepository
import hr.alenmagdic.githubapp.domain.usecase.GetUserDetailsUseCase

@Module
class UserModule {

    @Provides
    @ActivityScope
    fun provideGetUserDetailsUseCase(userRepository: UserRepository): GetUserDetailsUseCase {
        return GetUserDetailsUseCase(userRepository)
    }

    @Provides
    @ActivityScope
    fun provideUserDetailsPresenter(
        getUserDetailsUseCase: GetUserDetailsUseCase,
        userModelMapper: UserModelMapper
    ): UserDetailsContract.PresenterInterface {
        return UserDetailsPresenter(getUserDetailsUseCase, userModelMapper)
    }


}