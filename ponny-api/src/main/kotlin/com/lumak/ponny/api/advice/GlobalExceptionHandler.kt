package com.lumak.ponny.api.advice

import com.lumak.ponny.api.exception.ErrorCode
import com.lumak.ponny.api.exception.PonnyException
import com.lumak.ponny.api.rest.dto.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(PonnyException::class)
    fun handlePickupException(e: PonnyException): ResponseEntity<*> {
        logger.error("PonnyException ${e.message}")
        val response = ErrorResponse(e.code, e)
        return ResponseEntity(response, e.code.status)
    }

    /**
     * 아래 정의되지 않은 모든 예외를 서버 예외로 처리한다
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("PonnyException", e)
        val response = ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}