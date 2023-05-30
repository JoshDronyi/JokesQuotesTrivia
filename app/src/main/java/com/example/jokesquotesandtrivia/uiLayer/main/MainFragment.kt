package com.example.jokesquotesandtrivia.uiLayer.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.jokesquotesandtrivia.uiLayer.theme.JokesQuotesAndTriviaTheme


class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                JokesQuotesAndTriviaTheme {
                    HomeScreen(
                        onJokeRequest = {},
                        onQuoteRequest = { },
                        onTriviaRequest = { }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onJokeRequest: () -> Unit,
    onQuoteRequest: () -> Unit,
    onTriviaRequest: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Home Screen")

        Button(onClick = { onJokeRequest() }) {
            Text(text = "Get Joke")
        }
        Button(onClick = { onQuoteRequest() }) {
            Text(text = "Get Quote")
        }
        Button(onClick = { onTriviaRequest() }) {
            Text(text = "Get Trivia")
        }

    }

}