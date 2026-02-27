package com.ucamp.api.common.exception

open class UnauthorizedException(
    val errorCode: String,
    override val message: String
) : RuntimeException(message)