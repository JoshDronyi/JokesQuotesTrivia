package com.probro.trivia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
            setContent {
                val triviaState = triviaVM.triviaState.collectAsState()
                TriviaScreen(triviaState.value)
            }
        }
    }
}

@Composable
fun TriviaScreen(triviaState: TriviaState) {
    Card {
        Text(triviaState.questionList.joinToString())
    }
}
