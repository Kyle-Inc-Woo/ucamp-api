package com.ucamp.api.board.exception

import com.ucamp.api.common.exception.NotFoundException

class BoardNotFoundException(id: Long) : NotFoundException("Board not found id =$id")