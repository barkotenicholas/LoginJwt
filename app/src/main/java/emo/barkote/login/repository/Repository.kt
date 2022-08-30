package emo.barkote.login.repository

import emo.barkote.login.api.ApiService
import emo.barkote.login.api.models.Login
import emo.barkote.login.api.models.Signup
import javax.inject.Inject

open class Repository  @Inject constructor(private val apiService: ApiService) {

    suspend fun login(data: Login) = apiService.login(
        login = data
    )

    suspend fun signUp(data :Signup) = apiService.signUp(
        signup = data
    )
}