package com.example.datalayer

import com.example.datalayer.model.local.TriviaQuestion
import com.example.datalayer.model.remote.RetrofitClient
import com.example.datalayer.model.remote.dto.TriviaResponseDTO

object QuestionRepo {
    private val triviaService by lazy {
        RetrofitClient.triviaServiceInstance()
    }

    fun getQuickGame(gameSize: Int): Result<List<TriviaQuestion>> = with(triviaService) {
        val initialResponse = getQuickGame(gameSize).execute()
        if (initialResponse.isSuccessful) {
            val response = initialResponse.body() ?: TriviaResponseDTO()
            val questions = response.results.map { current ->
                with(current) {
                    TriviaQuestion(
                        category,
                        type,
                        difficulty,
                        question,
                        correctAnswer,
                        incorrectAnswers
                    )
                }
            }
            Result.success(questions)
        } else {
            Result.failure(Exception(initialResponse.message()))
        }
    }
}
