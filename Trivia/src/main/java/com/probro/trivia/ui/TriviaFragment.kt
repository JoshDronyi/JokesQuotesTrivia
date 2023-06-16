package com.probro.trivia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.probro.trivia.ui.screens.GameScreen
import com.probro.trivia.ui.screens.HomeScreen
import com.probro.trivia.ui.screens.ResultsScreen
import com.probro.trivia.util.TriviaScreens
import com.probro.trivia.viewmodel.TriviaViewModel

class TriviaFragment : Fragment() {

    private val triviaVM by viewModels<TriviaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            triviaVM.getTriviaQuestions()
            setContent {
                TriviaSectionHost()
            }
        }
    }
}

@Composable
fun TriviaSectionHost(
    triviaVM: TriviaViewModel = viewModel(),
    controller: NavHostController = rememberNavController(),
) {
    val state by triviaVM.triviaState.collectAsState()

    NavHost(
        navController = controller,
        startDestination = TriviaScreens.HOME_PAGE.route,
    ) {
        composable(TriviaScreens.HOME_PAGE.route) {
            HomeScreen() {
                triviaVM.changeNavEvent(it)
            }
        }
        composable(TriviaScreens.GAME_PAGE.route) {
            GameScreen(
                state,
            ) {
                triviaVM.answerCurrentQuestion(it)
            }
        }
        composable(TriviaScreens.RESULTS_PAGE.route) {
            ResultsScreen(
                state.correctAnswers,
                state.questionList,
            )
        }
    }

    LaunchedEffect(key1 = state.currentScreen) {
        controller.navigate(state.currentScreen.route)
    }
}
