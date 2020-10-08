package hr.alenmagdic.githubapp.data.dataacces.service

import hr.alenmagdic.githubapp.data.dto.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/users/{username}")
    fun findUser(
        @Path("username") repositoryName: String
    ): retrofit2.Call<UserDTO>

}