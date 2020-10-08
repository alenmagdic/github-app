package hr.alenmagdic.githubapp.app.dependencyinjection.module

import dagger.Module
import dagger.Provides
import hr.alenmagdic.githubapp.data.dataacces.dao.RepositoryDAO
import hr.alenmagdic.githubapp.data.dataacces.dao.UserDAO
import hr.alenmagdic.githubapp.data.dataacces.service.RepositoryService
import hr.alenmagdic.githubapp.data.dataacces.service.RetrofitServiceProvider
import hr.alenmagdic.githubapp.data.dataacces.service.UserService
import hr.alenmagdic.githubapp.data.dto.mapper.RepositoryMapper
import hr.alenmagdic.githubapp.data.dto.mapper.RepositorySearchResultsMapper
import hr.alenmagdic.githubapp.data.dto.mapper.UserMapper
import hr.alenmagdic.githubapp.data.repository.RepositoryRepositoryImpl
import hr.alenmagdic.githubapp.data.repository.UserRepositoryImpl
import hr.alenmagdic.githubapp.domain.repository.RepositoryRepository
import hr.alenmagdic.githubapp.domain.repository.UserRepository
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepositoryRepository(
        repositoryDAO: RepositoryDAO,
        repositorySearchResultsMapper: RepositorySearchResultsMapper,
        repositoryMapper: RepositoryMapper
    ): RepositoryRepository {
        return RepositoryRepositoryImpl(repositoryDAO, repositorySearchResultsMapper, repositoryMapper)
    }

    @Provides
    @Singleton
    fun provideRepositoryDAO(repositoryService: RepositoryService): RepositoryDAO {
        return RepositoryDAO(repositoryService)
    }

    @Provides
    @Singleton
    fun provideRepositoryService(): RepositoryService {
        return RetrofitServiceProvider.repositoryService
    }

    @Provides
    @Singleton
    fun provideRepositorySearchResultsMapper(repositoryMapper: RepositoryMapper): RepositorySearchResultsMapper {
        return RepositorySearchResultsMapper(repositoryMapper)
    }

    @Provides
    @Singleton
    fun provideRepositoryMapper(userMapper: UserMapper): RepositoryMapper {
        return RepositoryMapper(userMapper)
    }

    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDAO: UserDAO,
        userMapper: UserMapper
    ): UserRepository {
        return UserRepositoryImpl(userDAO, userMapper)
    }

    @Provides
    @Singleton
    fun provideUserDAO(userService: UserService): UserDAO {
        return UserDAO(userService)
    }

    @Provides
    @Singleton
    fun provideUserService(): UserService {
        return RetrofitServiceProvider.userService
    }
}