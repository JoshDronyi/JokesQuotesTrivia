package com.probro.trivia.ui

import com.probro.datalayer.model.local.TriviaQuestion
import com.probro.trivia.util.TriviaScreens

data class TriviaState(
    val isLoading: Boolean = false,
    val questionList: List<TriviaQuestion> = emptyList(),
    val currentQuestion: TriviaQuestion = TriviaQuestion.default(),
    val correctAnswers: Int = 0,
    val errorMsg: String = "",
    val currentScreen: TriviaScreens = TriviaScreens.HOME_PAGE,
)
