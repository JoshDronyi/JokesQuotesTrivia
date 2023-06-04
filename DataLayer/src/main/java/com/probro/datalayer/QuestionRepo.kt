package com.probro.datalayer

import com.probro.datalayer.model.local.TriviaQuestion
import com.probro.datalayer.model.remote.RetrofitClient
import com.probro.datalayer.model.remote.dto.TriviaResponseDTO
import com.probro.datalayer.model.remote.services.TriviaService

object QuestionRepo {

    private val triviaService by lazy<TriviaService> {
        RetrofitClient.triviaServiceInstance()
    }

    fun getQuickGame(gameSize: Int): Result<List<TriviaQuestion>> =
        with(triviaService) {
            val initialResponse = triviaService.getQuickGame(gameSize).execute()
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
                            incorrectAnswers,
                        )
                    }
                }
                Result.success(questions)
            } else {
                Result.failure(Exception(initialResponse.message()))
            }
        }
}
