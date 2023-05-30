package com.example.datalayer.model.remote.services

import com.example.datalayer.model.remote.dto.JokeDTO
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeRetrofitService : RetrofitService {
    @Headers(
        "Accept: application/json",
        "User-Agent: JokesQuotesAndTrivia (Practice App)"
    )
    @GET("/")
    suspend fun getJoke(): JokeDTO
}
