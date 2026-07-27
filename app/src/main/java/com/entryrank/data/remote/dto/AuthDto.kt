package com.entryrank.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class SignupRequest(
    val email: String,
    val password: String,
    val name: String
)

@Serializable
data class AuthResponse(
    val user: UserDto,
    val session: SessionDto
)

@Serializable
data class SessionDto(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("token_type")
    val tokenType: String
)

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val name: String? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("is_premium")
    val isPremium: Boolean = false,
    @SerialName("premium_expires_at")
    val premiumExpiresAt: String? = null
)
