package com.probro.datalayer.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaResponseDTO(
    @SerialName("response_code")
    val responseCode: Int = 0,
    val results: List<TriviaQuestionDTO> = emptyList()
)
