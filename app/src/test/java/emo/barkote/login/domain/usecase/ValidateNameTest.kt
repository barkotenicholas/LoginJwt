package emo.barkote.login.domain.usecase

import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidateNameTest{


    val username = ValidateName()
    @Test
    fun checkIfNameIsEmptyBoolean(){

        val name = ""

        val  result = username.execute(name)

        assertFalse(result.success)

    }

}