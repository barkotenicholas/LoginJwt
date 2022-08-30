package emo.barkote.login.api

import emo.barkote.login.api.models.Login
import emo.barkote.login.api.models.LoginResponse
import emo.barkote.login.api.models.SignUpResponse
import emo.barkote.login.api.models.Signup
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/signin")
    suspend fun login(@Body login: Login):Response<LoginResponse>

    @POST("/api/auth/signup")
    suspend fun signUp(@Body signup: Signup):Response<SignUpResponse>

}