package com.probro.trivia.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.probro.datalayer.model.local.TriviaQuestion

@Composable
fun ResultsScreen(correctAnswers: Int, questions: List<TriviaQuestion>) {
    Text(text = "Results screen: $correctAnswers/${questions.size} correct.")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultsScreenPreview() {
    ResultsScreen(
        0,
        listOf(TriviaQuestion.default()),
    )
}
