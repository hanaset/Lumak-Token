package com.lumak.ponny.api.exception

open class PonnyException(
    val code: ErrorCode,
    override val message: String? = code.message
): RuntimeException(
    message ?: code.message
)

class PonnyNotFoundTokenException: PonnyException(ErrorCode.NOT_FOUND_TOKEN)
class PonnyDeployException: PonnyException(ErrorCode.INTERNAL_SERVER_ERROR)
