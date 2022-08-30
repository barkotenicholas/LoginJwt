package emo.barkote.login.domain.usecase

import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ValidateEmailTest{

    private val email = ValidateEmail()

    @Test
    fun checkIfEmailIsEmptyReturnsFalseBoolean(){

        val emailString = ""

        val result = email.execute(emailString)

        assertFalse(result.success)
    }

    @Test
    fun checkIfEmailIsCorrectReturnFalse(){


        val emailExample = "nicholas"


        val result = email.execute(emailExample)

        assertFalse(result.success)

    }
    @Test
    fun checkIfEmailAddressIsCorrectReturnTrue(){
        val emailExample = "nicholas@gmail.com"


        val result = email.execute(emailExample)

        assertTrue(result.success)
    }
}