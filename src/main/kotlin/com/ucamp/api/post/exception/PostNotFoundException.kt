package com.ucamp.api.post.exception

import com.ucamp.api.common.exception.NotFoundException

class PostNotFoundException(id: Long) : NotFoundException(
    "Post not found by id $id"
)