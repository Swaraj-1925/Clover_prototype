package com.clovermusic.clover.data.mapper

import com.clovermusic.clover.data.local.entity.OwnerEntity
import com.clovermusic.clover.data.spotify.api.dto.common.OwnerResponseDto

fun OwnerResponseDto.toOwner(): OwnerEntity {
    return OwnerEntity(
        id = id,
        displayName = display_name,
        uri = uri
    )
}