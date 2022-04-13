package com.example.gallery2.features.openphoto.presentation

sealed class OpenPhotoState {
    class Success(
        val userName: String?,
        val openPhotoViewData: OpenPhotoViewData
    ) : OpenPhotoState()

    object Error : OpenPhotoState()
    object Loading : OpenPhotoState()
}