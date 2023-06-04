package com.probro.quotes.ui

import com.probro.datalayer.model.local.Quote

data class QuoteState(
    val isLoading: Boolean = false,
    val errorMsg: String = "",
    val quote: Quote? = null,
)
