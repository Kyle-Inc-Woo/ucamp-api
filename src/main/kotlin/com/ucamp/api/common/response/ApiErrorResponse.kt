package com.ucamp.api.common.response

import java.time.Instant

data class ApiErrorResponse(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String,
    val details: List<FieldErrorDetail> = emptyList()
)

data class FieldErrorDetail(
    val field: String,
    val rejectedValue: Any?,
    val reason: String
)
