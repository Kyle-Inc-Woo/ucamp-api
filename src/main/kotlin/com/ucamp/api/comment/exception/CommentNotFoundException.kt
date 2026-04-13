package com.ucamp.api.comment.exception

import com.ucamp.api.common.exception.NotFoundException

class CommentNotFoundException(id: Long) : NotFoundException("Comment with id $id not found")