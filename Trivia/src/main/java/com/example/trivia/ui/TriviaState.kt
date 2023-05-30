package com.example.trivia.ui

import com.example.datalayer.model.local.TriviaQuestion

data class TriviaState(
    val isLoading: Boolean = false,
    val questionList: List<com.example.datalayer.model.local.TriviaQuestion> = emptyList(),
    val errorMsg: String = ""
)
