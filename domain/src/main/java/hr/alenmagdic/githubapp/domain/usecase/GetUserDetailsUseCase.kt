package hr.alenmagdic.githubapp.domain.usecase

import hr.alenmagdic.githubapp.domain.model.User
import hr.alenmagdic.githubapp.domain.repository.UserRepository
import io.reactivex.Single

class GetUserDetailsUseCase(private val userRepository: UserRepository) {

    fun execute(username: String): Single<User> {
        return Single.fromCallable {
            userRepository.findUser(username)
        }
    }
}