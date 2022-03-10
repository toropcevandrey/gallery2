package com.example.gallery2.api.models

import com.google.gson.annotations.SerializedName

data class GetTokensModel(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    val scope: Any,
    @SerializedName("token_type")
    val tokenType: String
)