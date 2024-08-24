package com.clovermusic.clover.domain.usecase.app

import com.clovermusic.clover.data.repository.UserRepository
import javax.inject.Inject

class incrementNumClick @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(playlistId: String) {
        userRepository.incrementNumClick(playlistId)
    }
}
