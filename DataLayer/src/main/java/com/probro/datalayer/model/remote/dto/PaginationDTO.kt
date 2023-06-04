package com.probro.datalayer.model.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val currentPage: Int,
    val nextPage: Int?,
    val totalPages: Int
)
