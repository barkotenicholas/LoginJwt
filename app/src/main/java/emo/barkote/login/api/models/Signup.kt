package emo.barkote.login.api.models

import com.google.gson.annotations.SerializedName

data class Signup(
    @SerializedName("username") var username: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("roles") var roles: ArrayList<String> = arrayListOf()
)