package com.clovermusic.clover.data.spotify.api.dto.users

import com.clovermusic.clover.data.spotify.api.dto.common.FollowersResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.ImageResponseDto

data class UsersProfileResponseDto(
    val country: String,
    val display_name: String,
    val email: String,
    val followers: FollowersResponseDto,
    val href: String,
    val id: String,
    val images: List<ImageResponseDto>,
    val product: String?,
    val type: String,
    val uri: String
)