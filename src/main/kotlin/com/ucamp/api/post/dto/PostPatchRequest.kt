package com.ucamp.api.post.dto

data class PostPatchRequest(
    val title: String?,
    val content: String?,
    val isAnonymous: Boolean?
)
