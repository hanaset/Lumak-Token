package com.lumak.ponny.api.exception

open class PonnyException(
    val code: ErrorCode,
    override val message: String? = code.message
): RuntimeException(
    message ?: code.message
)
