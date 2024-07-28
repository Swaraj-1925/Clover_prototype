package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.users.UsersProfileResponseDto
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.model.UserProfile
// Object of UserProfileResponseDto are mapped into List of UserProfile
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