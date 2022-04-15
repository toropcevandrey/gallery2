package com.example.gallery2.features.addphoto

sealed class AddPhotoState {
    object Error : AddPhotoState()
    object Loading : AddPhotoState()
    object Success : AddPhotoState()
}