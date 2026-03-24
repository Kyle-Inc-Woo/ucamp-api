package com.ucamp.api.user.exception

import com.ucamp.api.common.exception.NotFoundException

class UserNotFoundException(userId: Long) : NotFoundException("User $userId not found")