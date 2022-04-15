package com.example.gallery2.base

sealed class ValidateState {

    object Success : ValidateState()
    object IsEmpty : ValidateState()
    object ComparePassword : ValidateState()
    object WrongEmail : ValidateState()
    object WrongOldPassword : ValidateState()
    object PasswordUpdated : ValidateState()
    object Clear : ValidateState()
}
