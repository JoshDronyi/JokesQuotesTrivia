package com.example.datalayer.model.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JokeDTO(
    val id: String = "",
    val joke: String = "",
    val status: Int = 0
)
