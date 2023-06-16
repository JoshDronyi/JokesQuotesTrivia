package com.probro.trivia.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probro.datalayer.QuestionRepo
import com.probro.datalayer.model.local.TriviaQuestion
import com.probro.trivia.ui.TriviaState
import com.probro.trivia.util.DEFAULT_GAME_SIZE
import com.probro.trivia.util.TriviaNavEvents
import com.probro.trivia.util.TriviaScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TriviaViewModel : ViewModel() {
    private val tag = "TriviaViewModel"
    private val _triviaState: MutableStateFlow<TriviaState> = MutableStateFlow(TriviaState())
    val triviaState: StateFlow<TriviaState> get() = _triviaState

    fun getTriviaQuestions(totalQuestions: Int = DEFAULT_GAME_SIZE) =
        viewModelScope.launch(Dispatchers.IO) {
            _triviaState.update {
                it.copy(isLoading = true)
            }

            val questionsResponse: Result<List<TriviaQuestion>> =
                QuestionRepo.getQuickGame(totalQuestions)

            Log.e(tag, "GETTING GAME QUESTIONS")

            if (questionsResponse.isSuccess) {
                _triviaState.update {
                    it.copy(
                        isLoading = false,
                        questionList = questionsResponse.getOrDefault(emptyList()),
                    )
                }
                Log.e(tag, "getTriviaQuestions: QUESTION RESPONSE RETURNS")
            } else {
                Log.e(
                    tag,
                    "getTriviaQuestions: WE HAD AN ERROR GETTING questions. $questionsResponse",
                )
            }
        }

    fun answerCurrentQuestion(userAnswer: String) {
        with(_triviaState) {
            if (userAnswer == _triviaState.value.currentQuestion.correctAnswer) {
                value = value.copy(
                    correctAnswers = value.correctAnswers + 1,
                )
            }
            val questionIndex = value.questionList.indexOf(value.currentQuestion)
            if (questionIndex < (value.questionList.size - 1)) {
                value = value.copy(
                    currentQuestion = value.questionList[questionIndex + 1],
                )
            } else {
                // Game over
                changeNavEvent(TriviaNavEvents.GameScreenToResultsScreen)
            }
        }
    }

    fun changeNavEvent(event: TriviaNavEvents) {
        with(_triviaState) {
            value = when (event) {
                TriviaNavEvents.GameScreenToResultsScreen -> value.copy(
                    currentScreen = TriviaScreens.RESULTS_PAGE,
                )

                TriviaNavEvents.HomeScreenToGameScreen -> value.copy(
                    currentScreen = TriviaScreens.GAME_PAGE,
                )

                TriviaNavEvents.ResultsScreenToHomeScreen -> value.copy(
                    currentScreen = TriviaScreens.HOME_PAGE,
                )
            }
        }
    }
}
