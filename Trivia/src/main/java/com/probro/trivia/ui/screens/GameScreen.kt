package com.probro.trivia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.probro.datalayer.model.local.TriviaQuestion
import com.probro.datalayer.utils.cleanText
import com.probro.trivia.ui.TriviaState

@Composable
fun GameScreen(
    triviaState: TriviaState,
    onAnswerSelect: (answer: String) -> Unit,
) {
    when {
        triviaState.isLoading -> {
            CircularProgressIndicator()
        }

        triviaState.errorMsg.isNotEmpty() -> {
            Text(text = "Exception: ${triviaState.errorMsg}")
        }

        triviaState.questionList.isNotEmpty() -> {
            QuestionDisplay(
                score = "${triviaState.correctAnswers}/${triviaState.questionList.size}",
                question = triviaState.currentQuestion,
                onAnswerSelect = onAnswerSelect,
            )
        }
    }
}

@Composable
fun QuestionDisplay(
    score: String = "",
    question: TriviaQuestion,
    modifier: Modifier = Modifier,
    onAnswerSelect: (answer: String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            text = score,
            modifier = Modifier.fillMaxWidth(),
        )
        QuestionTextBox(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(
                text = question.question.cleanText(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center),
            )
        }
        Divider(Modifier.fillMaxWidth(.75f))
        AnswerSection(
            question.correctAnswer,
            question.incorrectAnswers,
            onAnswerSelect = onAnswerSelect,
            modifier = Modifier.weight(1f),
        )
    }
}

const val ANSWER_COLUMN_COUNT = 4

@Composable
fun AnswerSection(
    correctAnswer: String,
    incorrectAnswers: List<String>,
    modifier: Modifier = Modifier,
    onAnswerSelect: (answer: String) -> Unit,
) {
    val answers = incorrectAnswers.toMutableList().apply {
        add(correctAnswer)
        shuffle()
    }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(answers) { answer ->
            AnswerButton(
                answerText = answer.cleanText(),
                onAnswerSelect = onAnswerSelect,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun AnswerButton(
    answerText: String,
    onAnswerSelect: (answer: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = { onAnswerSelect(answerText) },
        modifier = modifier
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        Text(
            text = answerText,
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
fun QuestionTextBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(
            MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium,
        ),
    ) {
        content()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuestionDisplayPreview() {
    val question = TriviaQuestion(
        category = "Entertainment: Music",
        type = "multiple",
        difficulty = "easy",
        question = (
            "In the  Rossini opera, what was the name of " +
                "&#039;The Barber of Seville&#039;?"
            ).cleanText(),
        correctAnswer = "Figaro",
        incorrectAnswers = listOf("Angelo", "Fernando", "Dave"),
    )
    QuestionDisplay(question = question) {
    }
}
