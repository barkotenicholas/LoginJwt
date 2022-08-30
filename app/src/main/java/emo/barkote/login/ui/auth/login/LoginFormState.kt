package emo.barkote.login.ui.auth.login

data class LoginFormState(
    var email : String = "",
    var emailError: String? = null,
    var password : String = "",
    var passwordError : String?=null
)
