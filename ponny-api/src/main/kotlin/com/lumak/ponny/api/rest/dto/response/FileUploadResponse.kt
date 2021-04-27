package com.lumak.ponny.api.rest.dto.response

data class FileUploadResponse(
    val writer: String,
    val title: String,
    val description: String,
    val imageUrl: String
)