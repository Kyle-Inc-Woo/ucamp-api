package com.ucamp.api.user.exception

import com.ucamp.api.common.exception.BadRequestException

class EmailAlreadyExistsException(email: String) :
    BadRequestException("Email already exists : $email")
