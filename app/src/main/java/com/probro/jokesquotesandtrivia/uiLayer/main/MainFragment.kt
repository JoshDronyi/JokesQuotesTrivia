package com.probro.jokesquotesandtrivia.uiLayer.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.probro.jokesquotesandtrivia.uiLayer.theme.JokesQuotesAndTriviaTheme

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val navController = findNavController()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                JokesQuotesAndTriviaTheme {
                    HomeScreen(
                        onJokeRequest = {
                            val action = MainFragmentDirections.actionMainFragmentToJokeGraph()
                            navController.navigate(action)
                        },
                        onQuoteRequest = {
                            val action = MainFragmentDirections.actionMainFragmentToQuoteGraph()
                            navController.navigate(action)
                        },
                        onTriviaRequest = {
                            val action = MainFragmentDirections.actionMainFragmentToTriviaGraph()
                            navController.navigate(action)
                        },
                    )
                }
            }
        }
    }
}
