package emo.barkote.login.ui.auth.signup

data class SignupFormState(
    var email : String = "",
    var emailError: String? = null,
    var password : String = "",
    var passwordError : String?=null,
    var role : String = "",
    var roleError:String?=null,
    var username :String = "",
    var usernameError : String?=null,
)
