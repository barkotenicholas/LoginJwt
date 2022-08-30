package emo.barkote.login.domain.usecase

import javax.inject.Inject

class ValidateRole @Inject constructor() {

    fun execute(role : String?) : ValidationResult {
        if(role.isNullOrEmpty()){
            return ValidationResult(
                success = false,
                errorMessage = "Choose a role"
            )
        }

        return ValidationResult(
            success = true
        )
    }

}