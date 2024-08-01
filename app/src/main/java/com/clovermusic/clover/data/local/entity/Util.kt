package com.clovermusic.clover.data.local.entity

import com.clovermusic.clover.data.spotify.api.dto.common.ImageResponseDto

object Util {
    fun List<ImageResponseDto>.toImagesEntity(ownerId: String, type: ImageType): List<ImageEntity> {
        return map { apiImage ->
            ImageEntity(
                height = apiImage.height,
                url = apiImage.url,
                width = apiImage.width,
                ownerId = ownerId,
                type = type
            )
        }
    }
}