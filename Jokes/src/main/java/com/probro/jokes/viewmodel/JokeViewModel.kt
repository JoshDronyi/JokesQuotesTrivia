package com.probro.jokes.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probro.datalayer.JokeRepo
import com.probro.datalayer.model.local.Joke
import com.probro.jokes.ui.JokeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    private val tag = "JokeViewModel"
    private val _jokeState: MutableStateFlow<JokeState> = MutableStateFlow(JokeState())
    val jokeState: StateFlow<JokeState> get() = _jokeState

    fun getRandomJoke() = viewModelScope.launch {
        _jokeState.update { it.copy(isLoading = false) }
        val jokeResponse = JokeRepo.getJoke().toEntity()

        _jokeState.update {
            val list = it.jokeList.toMutableList().apply {
                add(jokeResponse)
            }

            val source = list.first().source.url
            it.copy(
                isLoading = false,
                jokeList = list,
                jokeSource = source,
                pastSources = it.pastSources.toMutableSet().apply {
                    add(source)
                },
            )
        }
        Log.e(tag, "getRandomJoke: Joke was ${jokeResponse.joke}")
    }

    fun setAsFavorite(joke: Joke) {
        // TODO("Not yet implemented")
    }

    fun dislikeJoke(joke: Joke) {
        // TODO("Not yet implemented")
    }

    fun onShareSelect(joke: Joke) {
        // TODO("Not yet implemented")
    }
}
