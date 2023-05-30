package com.example.jokes.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokes.ui.JokeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    private val tag = "JokeViewModel"
    private val _jokeState: MutableStateFlow<com.example.jokes.ui.JokeState> = MutableStateFlow(com.example.jokes.ui.JokeState())
    val jokeState: StateFlow<com.example.jokes.ui.JokeState> get() = _jokeState

    fun getRandomJoke() = viewModelScope.launch {
        _jokeState.update { it.copy(isLoading = false) }
        val jokeResponse = com.example.datalayer.JokeRepo.getJoke()

        _jokeState.update {
            it.copy(isLoading = false, joke = jokeResponse.joke)
        }
        Log.e(tag, "getRandomJoke: Joke was ${jokeResponse.joke}")
    }
}
