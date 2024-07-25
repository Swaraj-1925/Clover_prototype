package com.clovermusic.clover.presentation.viewModel

//@HiltViewModel
//class Test @Inject constructor(
//    private val appRemote: AppRemote
//) : ViewModel() {
//    private val _appRemote = MutableStateFlow<SpotifyAppRemote?>(null)
//
//
//    fun connectToAppRemote() {
//        SpotifyAppRemote.disconnect(_appRemote.value)
//        viewModelScope.launch {
//            try {
//                _appRemote.value = appRemote.connectToAppRemote()
//            } catch (e: Exception) {
//                Log.e("Test", "connectToAppRemote: ", e)
//            }
//        }
//    }
//}