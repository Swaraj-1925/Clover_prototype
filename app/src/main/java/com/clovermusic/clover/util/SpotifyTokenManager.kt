package com.clovermusic.clover.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import javax.inject.Inject

class SpotifyTokenManager @Inject constructor(
    private val context: Context
) {
    private val _prefsName = "com.clovermusic.clover.PREFERENCES"
    private val _accessToken = "access_token"
    private val _refreshToken = "refresh_token"
    private val _tokenExpirationTime = "token_expiration_time"

    //    Function to create encrypted shared preferences
    private fun getEncryptedSharedPreferences(): SharedPreferences {
        val masterKeyAlias = MasterKey
            .Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            _prefsName,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    //    Function to save access token in shared preferences
    fun saveAccessToken(accessToken: String) {
        try {
            val editor = getEncryptedSharedPreferences().edit()
            editor.putString(_accessToken, accessToken)
            editor.apply()
            Log.i("TokenManager", "TOKEN SAVED: $accessToken")
        } catch (e: Exception) {
            Log.e("TokenManager", "Error saving access token: ${e.message}")
        } catch (e: IOException) {
            Log.e("TokenManager", "IoError saving access token: ${e.message}")
        }
    }


    //    Function to get saved access token from shared preferences
    fun getAccessToken(): String? {
        return getEncryptedSharedPreferences().getString(_accessToken, null)
    }

    fun getRefreshToken(): String? {
        return getEncryptedSharedPreferences().getString(_refreshToken, null)
    }

    fun saveRefreshToken(refreshToken: String) {
        try {
            val editor = getEncryptedSharedPreferences().edit()
            editor.putString(_refreshToken, refreshToken)
            editor.apply()
            Log.i("TokenManager", "Refresh token saved")
        } catch (e: Exception) {
            Log.e("TokenManager", "Error saving refresh token: ${e.message}")
        }
    }

    fun saveTokenExpirationTime(expirationTime: Long) {
        try {
            val editor = getEncryptedSharedPreferences().edit()
            editor.putLong(_tokenExpirationTime, expirationTime)
            editor.apply()
            Log.i("TokenManager", "Token expiration time saved: $expirationTime")
        } catch (e: Exception) {
            Log.e("TokenManager", "Error saving token expiration time: ${e.message}")
        }
    }

    fun getTokenExpirationTime(): Long {
        return getEncryptedSharedPreferences().getLong(_tokenExpirationTime, 0)
    }

    fun clearAllTokens() {
        val editor = getEncryptedSharedPreferences().edit()
        editor.remove(_accessToken)
        editor.remove(_refreshToken)
        editor.remove(_tokenExpirationTime)
        editor.apply()
        Log.i("TokenManager", "All tokens cleared")
    }
}