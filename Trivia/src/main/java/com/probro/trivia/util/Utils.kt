package com.probro.trivia.util

const val DEFAULT_GAME_SIZE: Int = 15
const val MIN_GAME_SIZE: Int = 10
const val MAX_GAME_SIZE: Int = 30

enum class TriviaScreens(val route: String) {
    HOME_PAGE("home"),
    GAME_PAGE("game"),
    RESULTS_PAGE("results"),
}

sealed class TriviaNavEvents {
    object HomeScreenToGameScreen : TriviaNavEvents()
    object GameScreenToResultsScreen : TriviaNavEvents()
    object ResultsScreenToHomeScreen : TriviaNavEvents()
}
