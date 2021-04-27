package com.lumak.ponny.api.rest.dto.response

data class KIP17MetaData(
    val writer: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val createdAt: Long
)