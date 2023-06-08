package com.probro.jokes.ui

import com.probro.datalayer.model.local.Joke

data class JokeState(
    val isLoading: Boolean = false,
    val jokeList: List<Joke> = emptyList(),
    val pastSources: Set<String> = emptySet(),
    val jokeSource: String = "",
    val errMsg: String = "",
)
