package com.ucamp.api.user.exception

import com.ucamp.api.common.exception.UnauthorizedException

class InvalidCredentialsException :
    UnauthorizedException("INVALID_CREDENTIAL", "Invalid email or password")
