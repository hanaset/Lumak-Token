package com.lumak.ponny.api.rest.dto.response

import com.lumak.ponny.api.exception.ErrorCode
import com.lumak.ponny.api.exception.PonnyException
import org.springframework.validation.BindingResult

class ErrorResponse private constructor(
    var errorCode: String,
    var reason: String,
    var errors: List<FieldError> = listOf()
) {

    constructor(code: ErrorCode) : this(reason = code.message, errorCode = code.name)


    constructor(code: ErrorCode, message: String) : this(reason = message, errorCode = code.name)

    constructor(code: ErrorCode, e: Exception) : this(code) {
        errors = FieldError.ofList(e)
    }

    constructor(code: ErrorCode, e: PonnyException) : this(code, e.message ?: code.message) {
        errors = FieldError.ofEmptyList()
    }

    class FieldError private constructor(
        val field: String,
        val value: String,
        val reason: String
    ) {
        companion object {
            fun ofList(bindingResult: BindingResult): List<FieldError> {
                return bindingResult.fieldErrors.map {
                    FieldError(
                        it.field, when (it.rejectedValue) {
                            is String -> it.rejectedValue as String
                            else -> ""
                        }, it.defaultMessage ?: ""
                    )
                }
            }


            fun ofList(e: Exception): List<FieldError> {
                return listOf(
                    FieldError(
                        field = "", value = "", reason = e.message
                            ?: "error message is empty"
                    )
                )
            }

            fun ofEmptyList(): List<FieldError> {
                return emptyList()
            }
        }
    }

}