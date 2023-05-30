package com.example.quotes.ui

import com.example.datalayer.model.local.Quote

data class QuoteState(
    val isLoading: Boolean = false,
    val errorMsg: String = "",
    val quote: com.example.datalayer.model.local.Quote? = null
)
