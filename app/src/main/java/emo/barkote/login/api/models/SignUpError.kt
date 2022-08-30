package emo.barkote.login.api.models

import com.google.gson.annotations.SerializedName

data class SignUpError (
    @SerializedName("message" ) var message : String? = null
)