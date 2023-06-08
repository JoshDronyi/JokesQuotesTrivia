package com.probro.jokes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.probro.datalayer.model.local.Joke
import com.probro.jokes.utils.JokeListEvents
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

                JokeHomeScreen(
                    jokeState = jokeState,
                    events = object : JokeListEvents {
                        override val onFavoriteSelect: (Joke) -> Unit
                            get() = { jokeVM.setAsFavorite(it) }
                        override val onShareSelect: (Joke) -> Unit
                            get() = { jokeVM.onShareSelect(it) }
                        override val onJokeDislike: (Joke) -> Unit
                            get() = { jokeVM.dislikeJoke(it) }
                        override val onJokeRequest: () -> Unit
                            get() = { jokeVM.getRandomJoke() }
                        override val navigateup: () -> Unit
                            get() = { findNavController().navigateUp() }
                    },
                )
            }
        }
    }
}
