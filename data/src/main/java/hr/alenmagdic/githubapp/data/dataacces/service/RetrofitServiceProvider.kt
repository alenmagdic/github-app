package hr.alenmagdic.githubapp.data.dataacces.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitServiceProvider {
    private const val BASE_URL = "https://api.github.com"

    private val retrofit: Retrofit
    val repositoryService: RepositoryService
    val userService: UserService

    init {
        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        repositoryService = retrofit.create(RepositoryService::class.java)
        userService = retrofit.create(UserService::class.java)
    }

}