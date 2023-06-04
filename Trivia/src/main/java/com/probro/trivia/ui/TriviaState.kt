package com.probro.trivia.ui

import com.probro.datalayer.model.local.TriviaQuestion

data class TriviaState(
    val isLoading: Boolean = false,
    val questionList: List<TriviaQuestion> = emptyList(),
    val errorMsg: String = ""
)
