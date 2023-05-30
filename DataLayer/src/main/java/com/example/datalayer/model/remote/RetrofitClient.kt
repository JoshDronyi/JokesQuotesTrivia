package com.example.datalayer.model.remote

import com.example.datalayer.model.remote.services.JokeRetrofitService
import com.example.datalayer.model.remote.services.QuoteRetrofitService
import com.example.datalayer.model.remote.services.TriviaService
import com.example.datalayer.utils.JOKE_BASE_URL
import com.example.datalayer.utils.QUOTE_BASE_URL
import com.example.datalayer.utils.TRIVIA_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object RetrofitClient {
    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val mediaType = "application/json".toMediaType()

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .client(client)
        .addConverterFactory(Json.asConverterFactory(mediaType))

    fun jokeServiceInstance(): JokeRetrofitService =
        retrofitBuilder
            .baseUrl(JOKE_BASE_URL)
            .build()
            .create()

    fun quoteServiceInstance(): QuoteRetrofitService =
        retrofitBuilder
            .baseUrl(QUOTE_BASE_URL)
            .build()
            .create()

    fun triviaServiceInstance(): TriviaService =
        retrofitBuilder
            .baseUrl(TRIVIA_BASE_URL)
            .build()
            .create()
}
