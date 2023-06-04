package com.probro.datalayer.model.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaQuestionDTO(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>
)
{
}