package hr.alenmagdic.githubapp.domain.usecase

import hr.alenmagdic.githubapp.domain.model.Repository
import hr.alenmagdic.githubapp.domain.repository.RepositoryRepository
import io.reactivex.Single

class GetRepositoryDetailsUseCase(private val repository: RepositoryRepository) {

    fun execute(repositoryName: String, ownerName: String): Single<Repository> {
        return Single.fromCallable {
            repository.findRepository(repositoryName, ownerName)
        }
    }
}