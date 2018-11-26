package com.uttampanchasara.network.response

data class ErrorResponse(
        val code: String,
        val message: String,
        val type: String
)