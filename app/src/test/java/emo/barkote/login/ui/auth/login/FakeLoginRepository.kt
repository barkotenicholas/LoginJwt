package emo.barkote.login.ui.auth.login

import emo.barkote.login.api.models.Login
import emo.barkote.login.api.models.LoginResponse

class FakeLoginRepository  {

    private val loginDetails = mutableListOf<Login>()

    private var shouldReturnNetworkError = false

    init {
        loginDetails.add(
            Login(
                "johndoe@gmail.com",
                "Barpello@329"
            )
        )
    }

    fun shouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = true
    }


    suspend fun login(login: Login): LoginResponse {

        if (loginDetails.contains(login)){
            val roles = arrayListOf(
                "admin"
            )
            return LoginResponse(
                id = 1,
                username = "johndoe",
                email = "johndoe@gmail.com",
                roles = roles,
                accessToken = "alfllbvFVQDVQB"
            )
        }

        return LoginResponse(
            id = null,
            username = null,
            email = null,
            roles = arrayListOf(
                "none"
            ),
            accessToken = null
        )
    }

}