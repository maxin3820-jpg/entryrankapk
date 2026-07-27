package com.entryrank.data.remote.api

import com.entryrank.data.remote.dto.*
import retrofit2.http.*

interface AuthApi {
    
    @POST("auth/v1/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): AuthResponse
    
    @POST("auth/v1/token?grant_type=password")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse
    
    @POST("auth/v1/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    )
    
    @GET("auth/v1/user")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): UserDto
    
    @POST("auth/v1/token?grant_type=refresh_token")
    suspend fun refreshToken(
        @Body refreshToken: Map<String, String>
    ): AuthResponse
    
    @PUT("rest/v1/profiles")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body profile: Map<String, Any>
    ): UserDto
}
