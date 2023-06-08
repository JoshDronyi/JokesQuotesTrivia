package com.probro.datalayer.model.remote.services

import com.probro.datalayer.model.remote.dto.JokeDTO
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeService : RetrofitService {

    @Headers(
        "Accept: application/json",
        "User-Agent: JokesQuotesAndTrivia (Practice App)",
    )
    @GET("/")
    suspend fun getJoke(): JokeDTO
}
