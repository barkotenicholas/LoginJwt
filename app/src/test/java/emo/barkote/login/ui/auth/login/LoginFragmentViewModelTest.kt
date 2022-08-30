package emo.barkote.login.ui.auth.login

import emo.barkote.login.domain.usecase.ValidateEmail
import emo.barkote.login.domain.usecase.ValidatePassword
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginFragmentViewModelTest{

    private lateinit var viewModel: LoginFragmentViewModel
    private val email = ValidateEmail()
    private val password = ValidatePassword()
    @Before
    fun setUp(){
        viewModel = LoginFragmentViewModel(
            email,
            password,
            FakeLoginRepository()
        )
    }

}