package emo.barkote.login.api.models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message" ) var message : String? = null

)