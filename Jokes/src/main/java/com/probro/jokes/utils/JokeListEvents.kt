package com.probro.jokes.utils

import com.probro.datalayer.model.local.Joke

interface JokeListEvents {
    val onFavoriteSelect: (Joke) -> Unit
    val onShareSelect: (Joke) -> Unit
    val onJokeDislike: (Joke) -> Unit
    val onJokeRequest: () -> Unit
    val navigateup: () -> Unit
}
