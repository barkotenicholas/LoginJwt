package emo.barkote.login.api.models

import com.google.gson.annotations.SerializedName

data class LoginError(
    @SerializedName("accessToken") var accessToken : String?= null,
    @SerializedName("message" ) var message : String? = null
)