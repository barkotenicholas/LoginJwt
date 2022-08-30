package emo.barkote.login.domain.usecase

import javax.inject.Inject

class ValidateName @Inject constructor() {

    fun execute(name :String?) : ValidationResult{

        if (name.isNullOrEmpty()) {

            return ValidationResult(
                success = false,
                errorMessage = "Username is empty"
            )
        }

        return ValidationResult(
            success = true
        )
    }
}