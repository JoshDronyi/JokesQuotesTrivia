package com.example.jokesquotesandtrivia.util


fun String.cleanText(): String {
    return this.replace("&quot;", "\"")
        .replace("&#039;", "\'")
        .replace("&amp;", "&")
}