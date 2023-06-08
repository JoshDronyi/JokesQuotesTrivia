package com.probro.datalayer.model.remote.services

import com.probro.datalayer.model.remote.dto.TriviaResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService : RetrofitService {
    @GET(TRIVIA_ENDPOINT)
    fun getQuickGame(@Query("amount") amount: Int): Call<TriviaResponseDTO>

    companion object {
        const val TRIVIA_ENDPOINT = "api.php"
    }
}
