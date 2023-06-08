package com.probro.datalayer

import com.probro.datalayer.model.remote.RetrofitClient
import com.probro.datalayer.model.remote.services.JokeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object JokeRepo {
    private val jokeService: JokeService =
        RetrofitClient.jokeServiceInstance()

    suspend fun getJoke() = withContext(Dispatchers.IO) {
        jokeService.getJoke()
    }
}
