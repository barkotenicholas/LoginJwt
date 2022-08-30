package emo.barkote.login.domain.usecase

import androidx.core.util.PatternsCompat
import javax.inject.Inject

class ValidateEmail @Inject constructor(){

    fun execute(email:String?): ValidationResult {

        if(email.isNullOrEmpty()){
            return ValidationResult(
                success = false,
                errorMessage = "The Email can not be Blank"
            )
        }


        if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                success = false,
                errorMessage = "That's not a valid email"
            )
        }


        return ValidationResult(
            success = true
        )
    }


}