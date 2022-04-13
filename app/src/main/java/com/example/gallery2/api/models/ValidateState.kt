package com.example.gallery2.api.models

sealed class ValidateState {

    object Success : ValidateState()
    object IsEmpty : ValidateState()
    object ComparePassword : ValidateState()
    object WrongEmail : ValidateState()
    object WrongOldPassword : ValidateState()
    object PasswordUpdated : ValidateState()
    object Clear : ValidateState()
}
