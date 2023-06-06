package com.probro.jokes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.probro.jokes.viewmodel.JokeViewModel

class JokeFragment : Fragment() {

    private val jokeVM by viewModels<JokeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val jokeState by jokeVM.jokeState.collectAsState()
                JokeScreen(jokeState) {
                    jokeVM.getRandomJoke()
                }
            }
        }
    }
}

@Composable
fun JokeScreen(
    jokeState: JokeState,
    onJokeRequest: () -> Unit,
) {
    Card() {
        Text(text = jokeState.joke)
    }

    Button(onClick = { onJokeRequest() }) {
        Text("Another Joke?")
    }
}
