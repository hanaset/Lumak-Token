package com.lumak.ponny.api.rest.dto.request

data class FileUploadRequest(
    val writer: String,
    val title: String,
    val description: String
)