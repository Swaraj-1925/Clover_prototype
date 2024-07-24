package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.common.Image

data class UserProfile(
    val displayName: String,
    val email: String,
    val followers: Int?,
    val id: String,
    val images: List<Image>,
    val product: String?,
    val type: String,
    val uri: String
)
