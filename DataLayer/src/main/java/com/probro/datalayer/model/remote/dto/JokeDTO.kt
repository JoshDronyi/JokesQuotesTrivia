package com.probro.datalayer.model.remote.dto

import com.probro.datalayer.model.local.Joke
import kotlinx.serialization.Serializable

@Serializable
data class JokeDTO(
    val id: String = "",
    val source: JokeSource = JokeSource.DadJokes,
    val joke: String = "",
    val status: Int = 0,
) {
    fun toEntity() = with(this) {
        Joke(id = id, joke = joke, status = status, source = source)
    }
}

enum class JokeSource(val url: String) {
    DadJokes("icanhazdadjokes.com"),
}
