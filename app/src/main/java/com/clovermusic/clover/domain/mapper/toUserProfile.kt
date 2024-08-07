package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.spotify.api.dto.users.UsersProfileResponseDto
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.model.UserProfile

fun UsersProfileResponseDto.toUserProfile(): UserProfile {
    return UserProfile(
        displayName = display_name,
        email = email,
        followers = followers.total,
        id = id,
        images = images.toImages(),
        product = product,
        type = type,
        uri = uri
    )
}