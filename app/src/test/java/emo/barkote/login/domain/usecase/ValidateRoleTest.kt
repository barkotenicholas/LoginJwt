package emo.barkote.login.domain.usecase

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidateRoleTest{
    val username = ValidateRole()
    @Test
    fun checkIfNameIsEmptyBoolean(){

        val role = ""

        val  result = username.execute(role)

        Assert.assertFalse(result.success)

    }

}