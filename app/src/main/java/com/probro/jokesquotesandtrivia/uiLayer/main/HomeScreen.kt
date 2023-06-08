package com.probro.jokesquotesandtrivia.uiLayer.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


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