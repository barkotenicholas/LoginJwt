package emo.barkote.login.ui.auth.signup


sealed class SignupFormEvent {
    data class EmailChanged(val email:String): SignupFormEvent()
    data class PasswordChanged(val password:String): SignupFormEvent()
    data class UserNameChanged(val username:String): SignupFormEvent()
    data class RoleChanged(val role:String): SignupFormEvent()
    object Submit: SignupFormEvent()

}