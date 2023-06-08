package com.probro.datalayer.model.remote.services

import com.probro.datalayer.model.remote.dto.QuoteResponseDTO
import retrofit2.http.GET

interface QuoteRetrofitService : RetrofitService {
    @GET(RANDOM_QUOTE_ENDPOINT)
    suspend fun getQuote(): QuoteResponseDTO

    companion object {
        const val RANDOM_QUOTE_ENDPOINT = "quotes/random"
    }
}
