package emo.barkote.login.domain.usecase

import javax.inject.Inject

class ValidatePassword  @Inject constructor(){

    fun execute(password:String?): ValidationResult {

        if(password.isNullOrEmpty()){
            return ValidationResult(
                success = false,
                errorMessage = "Password is empty"
            )
        }

        if(password.length < 8){
            return ValidationResult(
                success = false,
                errorMessage = "The password needs to consist of 8 characters"
            )
        }

        val containsLettersAndDigits = password.any { it.isDigit() }
                && password.any { it.isLetter() }

        if(!containsLettersAndDigits){
            return ValidationResult(
                success = false,
                errorMessage = "The password needs to contain one letter and digit"
            )
        }

        return ValidationResult(
            success = true
        )
    }



}