package hr.alenmagdic.githubapp.app.dependencyinjection.component

import dagger.Component
import hr.alenmagdic.githubapp.app.dependencyinjection.module.AppModule
import hr.alenmagdic.githubapp.domain.repository.RepositoryRepository
import hr.alenmagdic.githubapp.domain.repository.UserRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun repositoryRepository(): RepositoryRepository
    fun userRepository(): UserRepository
}