package com.example.jokes.ui

data class JokeState(
    val isLoading: Boolean = false,
    val joke: String = "",
    val errMsg: String = ""
)
