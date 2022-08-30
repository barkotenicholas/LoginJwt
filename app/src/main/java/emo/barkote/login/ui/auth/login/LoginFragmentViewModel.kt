package emo.barkote.login.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import emo.barkote.login.api.models.Login
import emo.barkote.login.api.models.LoginError
import emo.barkote.login.api.models.LoginResponse
import emo.barkote.login.domain.usecase.ValidateEmail
import emo.barkote.login.domain.usecase.ValidatePassword
import emo.barkote.login.repository.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private var repository: Repository
) : ViewModel() {

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(LoginFormState())
    var state: StateFlow<LoginFormState> = _state

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            LoginFormEvent.Submit -> {

                submitData()
            }

        }
    }

    fun goToSignUp() = viewModelScope.launch {
        validationEventChannel.send(ValidationEvent.NavigateToLogin)
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(_state.value.email)
        val passwordResult = validatePassword.execute(_state.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.success }

        if (hasError) {
            if (emailResult.errorMessage != null) {
              _state.value.emailError = emailResult.errorMessage
                showEmailError(emailResult.errorMessage)
            }
            if (passwordResult.errorMessage != null) {
                _state.value.passwordError = passwordResult.errorMessage
                showPasswordError(passwordResult.errorMessage)
            }
            return
        }

        viewModelScope.launch {
            val login =
                Login(username = _state.value.email, password = _state.value.password)
            val response = repository.login(login)
            if (response.isSuccessful) {
                val result = response.body()
                validationEventChannel.send(ValidationEvent.NavigateToHome(result))
            } else{
                val error:LoginError = Gson().fromJson(response.errorBody()?.charStream(),LoginError::class.java)
                when(response.code()){
                    401 ->{
                        validationEventChannel.send(ValidationEvent.InvalidPassword(error.message))
                    }
                    404 ->{
                        validationEventChannel.send(ValidationEvent.UserNotFound(error.message))
                    }
                    500 ->{
                        validationEventChannel.send(ValidationEvent.Unexpected(error.message))
                    }
                }
            }
        }


    }

    private fun showPasswordError(errorMessage: String) = viewModelScope.launch{
        validationEventChannel.send(ValidationEvent.ShowPasswordError(errorMessage))
    }

    private fun showEmailError(errorMessage: String) = viewModelScope.launch {
        validationEventChannel.send(ValidationEvent.ShowEmailError(errorMessage))
    }
    sealed class ValidationEvent {
        data class ShowEmailError(val msg:String): ValidationEvent()
        data class ShowPasswordError(val msg:String): ValidationEvent()
        data class UserNotFound(val message: String?) : ValidationEvent()
        data class InvalidPassword(val message: String?) : ValidationEvent()
        data class Unexpected(val message: String?) : ValidationEvent()
        data class NavigateToHome(val login: LoginResponse?) : ValidationEvent()
        object NavigateToLogin : ValidationEvent()
        object Success : ValidationEvent()
    }

}