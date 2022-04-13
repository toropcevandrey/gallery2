package com.example.gallery2.features.addphoto.presentation

sealed class AddPhotoState {
    object Error : AddPhotoState()
    object Loading : AddPhotoState()
    object Success : AddPhotoState()
    object FirstInit : AddPhotoState()
}