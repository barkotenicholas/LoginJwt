package emo.barkote.login.domain.usecase

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatePasswordTest {

    private val passTest = ValidatePassword()

    @Test
    fun checkIfPasswordIsNullReturnsBoolean() {

        val password = ""
        val result = passTest.execute(password)
        assertFalse(result.success)
        assertEquals(result.errorMessage, "Password is empty")
    }

    @Test
    fun checkIfPasswordIsMoreThan8CharactersReturnsFalse() {
        val password = "asdqweq"
        val result = passTest.execute(password)
        assertFalse(result.success)
        assertEquals(result.errorMessage, "The password needs to consist of 8 characters")
    }

    @Test
    fun checkIfPasswordContainsDigitsCharactersAndLettersBoolean() {
        val password = "12345678"
        val result = passTest.execute(password)
        assertFalse(result.success)
        assertEquals(result.errorMessage, "The password needs to contain one letter and digit")

    }

    @Test
    fun checkIfPasswordValidationWorksBoolean(){
        val password = "Barpello@329"
        val result = passTest.execute(password)
        assertTrue(result.success)
    }

}