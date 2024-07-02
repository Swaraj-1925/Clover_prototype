package com.clovermusic.clover.data.spotify.persistence

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject


/**
 * Object to handle token  received after authentication
 */
class TokenManager @Inject constructor(private val context: Context) {

    private val _prefsName = "com.clovermusic.clover.PREFERENCES"
    private val _token = "access_token"

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
        val editor = getEncryptedSharedPreferences().edit()
        editor.putString(_token, accessToken)
        editor.apply()
        Log.i("TokenManegar", "TOKEN SAVED$accessToken")
    }


    //    Function to get saved access token from shared preferences
    fun getAccessToken(): String? {
        return getEncryptedSharedPreferences().getString(_token, null)
    }

    //    Function to delete access token from shared preferences
    fun clearAccessToken() {
        val editor = getEncryptedSharedPreferences().edit()
        editor.remove(_token)
        editor.apply()
    }

}