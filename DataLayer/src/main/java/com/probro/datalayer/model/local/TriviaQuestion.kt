package com.probro.datalayer.model.local

data class TriviaQuestion(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
) {
    companion object {
        fun default() = TriviaQuestion(
            category = "Jawnski jawn jawn",
            type = "Rhetorical",
            difficulty = "17.9/56.387",
            question = "What it iiiiiiisss playaaa???",
            correctAnswer = "What it do homeboy!?!?",
            incorrectAnswers = listOf(
                "a rhetorical question",
                "a question of existential crisis.",
                "Where am I???!?!?!",
            ),
        )
    }
}
