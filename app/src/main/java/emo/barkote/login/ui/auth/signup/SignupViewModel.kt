package emo.barkote.login.ui.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import emo.barkote.login.api.models.SignUpError
import emo.barkote.login.api.models.Signup
import emo.barkote.login.domain.usecase.ValidateEmail
import emo.barkote.login.domain.usecase.ValidateName
import emo.barkote.login.domain.usecase.ValidatePassword
import emo.barkote.login.domain.usecase.ValidateRole
import emo.barkote.login.repository.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateUsername: ValidateName,
    private val validateRole: ValidateRole,
    private val repository: Repository,
) : ViewModel() {


    private var _state = MutableStateFlow(SignupFormState())
    val state: StateFlow<SignupFormState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun onEvent(event: SignupFormEvent) {

        when (event) {
            is SignupFormEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)

            }
            is SignupFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)

            }
            is SignupFormEvent.RoleChanged -> {
                _state.value = _state.value.copy(role = event.role)

            }
            is SignupFormEvent.UserNameChanged -> {
                _state.value = _state.value.copy(username = event.username)

            }
            SignupFormEvent.Submit -> {
                submitData()
            }

        }

    }

    private fun submitData() {
        val emailResult = validateEmail.execute(_state.value.email)
        val passwordResult = validatePassword.execute(_state.value.password)
        val userNameResult = validateUsername.execute(_state.value.username)
        val roleResult = validateRole.execute(_state.value.role)

        val hasError = listOf(
            emailResult,
            passwordResult,
            userNameResult,
            roleResult
        ).any { !it.success }

        Log.i("TAG", "submitData: $hasError")
        Log.i("TAG", "submitData: ${emailResult.errorMessage}")

        if (hasError) {
            if (!emailResult.success) {
                _state.value = _state.value.copy(emailError = emailResult.errorMessage)
                showEmailError(emailResult.errorMessage)
            }
            if (!passwordResult.success) {

                _state.value = _state.value.copy(passwordError = passwordResult.errorMessage)

                showPasswordError(passwordResult.errorMessage)
            }
            if (!userNameResult.success) {
                _state.value = _state.value.copy(usernameError = userNameResult.errorMessage)
                showUserNameError(userNameResult.errorMessage)

            }
            if (!roleResult.success) {
                _state.value = _state.value.copy(roleError = roleResult.errorMessage)
                showRoleError(roleResult.errorMessage)
            }

            return
        }


        viewModelScope.launch {

            val role = arrayListOf(
                _state.value.role
            )
            val signup = Signup(_state.value.username,
                _state.value.email,
                _state.value.password,
                role)

            val response = repository.signUp(signup)

            if(response.isSuccessful){
                val result = response.body()
                if (result != null) {
                    validationEventChannel.send(ValidationEvent.Success(result.message!!))
                    validationEventChannel.send(ValidationEvent.SuccessGoToLogin)

                }
            }else{
                val error :SignUpError =
                    Gson().fromJson(response.errorBody()?.charStream(),
                    SignUpError::class.java)

                when(response.code()){
                    500->{
                        validationEventChannel.send(ValidationEvent.ShowSignUpError(error.message!!))
                    }
                }
            }
        }

    }

    private fun showRoleError(errorMessage: String?) = viewModelScope.launch {
        validationEventChannel.send(ValidationEvent.ShowRole(errorMessage.toString()))
    }

    private fun showUserNameError(errorMessage: String?) = viewModelScope.launch {
        validationEventChannel.send(ValidationEvent.ShowUserNameError(errorMessage.toString()))
    }

    private fun showPasswordError(errorMessage: String?) = viewModelScope.launch {
        validationEventChannel.send(ValidationEvent.ShowPasswordError(errorMessage.toString()))
    }

    private fun showEmailError(errorMessage: String?) = viewModelScope.launch {

        validationEventChannel.send(ValidationEvent.ShowEmailError(errorMessage.toString()))
    }


    sealed class ValidationEvent {
        object SuccessGoToLogin: ValidationEvent()
        data class ShowEmailError(val msg: String) : ValidationEvent()
        data class ShowSignUpError(val msg: String) : ValidationEvent()
        data class ShowPasswordError(val msg: String) : ValidationEvent()
        data class ShowUserNameError(val msg: String) : ValidationEvent()
        data class ShowRole(val msg: String) : ValidationEvent()
        data class Success(val msg: String) : ValidationEvent()
    }

}