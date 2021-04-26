package com.lumak.ponny.api.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "Not Found Token")
}