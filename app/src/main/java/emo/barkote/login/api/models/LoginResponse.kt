package emo.barkote.login.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("username") var username: String?  = null,
    @SerializedName("email") var email : String?  = null,
    @SerializedName("roles") var roles : ArrayList<String> = arrayListOf(),
    @SerializedName("accessToken") var accessToken : String?= null,
) : Parcelable