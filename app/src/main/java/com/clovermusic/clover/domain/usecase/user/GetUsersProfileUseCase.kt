package com.clovermusic.clover.domain.usecase.user

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class GetUsersProfileUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
//    suspend operator fun invoke(userId: String): UserProfile {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            val res = repository.getUsersProfile(userId)
//            res.toUserProfile()
//        }.onFailure { e ->
//            Log.e("GetUserProfileUseCase", "Error fetching user profile", e)
//        }.getOrThrow()
//    }

}