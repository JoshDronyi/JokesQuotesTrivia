package com.example.quotes.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.model.remote.RetrofitClient
import com.example.datalayer.model.remote.dto.Quote
import com.example.jokesquotesandtrivia.util.STATUSCODE_OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuoteViewModel : ViewModel() {

    val JOSH_TAG = "JOSH"

    val _currentQuote: MutableStateFlow<Quote> = MutableStateFlow(
        Quote()
    )
    val currentQuote: StateFlow<Quote> get() = _currentQuote

    // Api for dictum is down ( returns 404)
    fun getQuote() = viewModelScope.launch(Dispatchers.IO) {
        val quoteResponse =
            RetrofitClient.quoteServiceInstance().getQuote()

        withContext(Dispatchers.Main) {
            if (quoteResponse.statusCode == STATUSCODE_OK) {
                _currentQuote.value = quoteResponse.quotes[0]
            }
        }
        Log.e(JOSH_TAG, "Current quote was ${currentQuote.value}")
    }
}
