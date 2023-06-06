package com.probro.quotes.viewmodel

import androidx.lifecycle.ViewModel
import com.probro.datalayer.model.local.Quote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuoteViewModel : ViewModel() {

    val JOSH_TAG = "JOSH"

    val _currentQuote: MutableStateFlow<Quote> = MutableStateFlow(
        Quote(),
    )
    val currentQuote: StateFlow<Quote> get() = _currentQuote

    // Api for dictum is down ( returns 404)
    /* fun getQuote() = viewModelScope.launch(Dispatchers.IO) {
         val quoteResponse =
             RetrofitClient.quoteServiceInstance().getQuote()

         withContext(Dispatchers.Main) {
             if (quoteResponse.statusCode == STATUSCODE_OK) {
                 _currentQuote.value = quoteResponse.quotes.first()
             }
         }
         Log.e(JOSH_TAG, "Current quote was ${currentQuote.value}")
     }*/
}
