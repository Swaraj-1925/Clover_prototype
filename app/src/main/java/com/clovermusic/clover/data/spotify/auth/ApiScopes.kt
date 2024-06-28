package com.clovermusic.clover.data.spotify.auth

/*
Spotify authentication scope Access need from spotify of user data for app
 */
enum class ApiScopes(val scope: String) {
    USER_READ_PRIVATE("user-read-private"),                         // Access user's subscription details
    USER_READ_EMAIL("user-read-email"),                             // Access user's email address
    PLAYLIST_READ_PRIVATE("playlist-read-private"),                 // Access user's private playlists
    PLAYLIST_READ_COLLABORATIVE("playlist-read-collaborative"),     // Access user's collaborative playlists
    PLAYLIST_MODIFY_PUBLIC("playlist-modify-public"),               // Create/modify public playlists
    PLAYLIST_MODIFY_PRIVATE("playlist-modify-private"),             // Create/modify private playlists
    USER_LIBRARY_READ("user-library-read"),                         // Access user's saved content
    USER_LIBRARY_MODIFY("user-library-modify"),                     // Modify user's saved content
    USER_TOP_READ("user-top-read"),                                 // Access user's top artists and tracks
    USER_READ_RECENTLY_PLAYED("user-read-recently-played"),         // Access user's recently played content
    USER_FOLLOW_READ("user-follow-read"),                           // Access user's followed artists
    USER_FOLLOW_MODIFY("user-follow-modify"),                       // Modify user's followed artists
    UGC_IMAGE_UPLOAD("ugc-image-upload");                           // Upload images (e.g., playlist covers)

//    takes all the scopes above and convert them from string to an array
    companion object {
        fun getAllScopes(): Array<String> {
            return entries.map { it.scope }.toTypedArray()
        }
    }
}