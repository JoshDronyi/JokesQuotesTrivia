package com.probro.datalayer.model.local

import com.probro.datalayer.model.remote.dto.JokeSource

data class Joke(
    val id: String = "",
    val joke: String = "",
    val source: JokeSource = JokeSource.DadJokes,
    val status: Int = 0,
)
