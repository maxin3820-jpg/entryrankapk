package com.entryrank.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.entryrank.data.local.dao.UserDao
import com.entryrank.data.local.entity.UserEntity
import com.entryrank.data.remote.api.AuthApi
import com.entryrank.data.remote.api.TokenProvider
import com.entryrank.data.remote.dto.LoginRequest
import com.entryrank.data.remote.dto.SignupRequest
import com.entryrank.data.remote.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>
) : TokenProvider {
    
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private const val API_KEY = "YOUR_SUPABASE_ANON_KEY" // Replace with actual key
    }
    
    override suspend fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }
    
    override fun getApiKey(): String = API_KEY
    
    suspend fun login(email: String, password: String): Result<UserEntity> {
        return try {
            val response = authApi.login(LoginRequest(email, password))
            
            // Save tokens
            dataStore.edit { preferences ->
                preferences[TOKEN_KEY] = response.session.accessToken
                preferences[REFRESH_TOKEN_KEY] = response.session.refreshToken
                preferences[USER_ID_KEY] = response.user.id
            }
            
            // Save user to local database
            val userEntity = UserEntity(
                id = response.user.id,
                email = response.user.email,
                name = response.user.name,
                isPremium = response.user.isPremium,
                premiumExpiresAt = response.user.premiumExpiresAt,
                createdAt = response.user.createdAt,
                updatedAt = System.currentTimeMillis()
            )
            userDao.insertUser(userEntity)
            
            Result.success(userEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun signup(email: String, password: String, name: String): Result<UserEntity> {
        return try {
            val response = authApi.signup(SignupRequest(email, password, name))
            
            // Save tokens
            dataStore.edit { preferences ->
                preferences[TOKEN_KEY] = response.session.accessToken
                preferences[REFRESH_TOKEN_KEY] = response.session.refreshToken
                preferences[USER_ID_KEY] = response.user.id
            }
            
            // Save user to local database
            val userEntity = UserEntity(
                id = response.user.id,
                email = response.user.email,
                name = response.user.name,
                isPremium = response.user.isPremium,
                premiumExpiresAt = response.user.premiumExpiresAt,
                createdAt = response.user.createdAt,
                updatedAt = System.currentTimeMillis()
            )
            userDao.insertUser(userEntity)
            
            Result.success(userEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout(): Result<Unit> {
        return try {
            val token = getToken().first() ?: return Result.failure(Exception("No token"))
            authApi.logout("Bearer $token")
            
            // Clear tokens
            dataStore.edit { preferences ->
                preferences.clear()
            }
            
            // Clear local database
            userDao.deleteAllUsers()
            
            Result.success(Unit)
        } catch (e: Exception) {
            // Clear tokens anyway
            dataStore.edit { preferences ->
                preferences.clear()
            }
            Result.failure(e)
        }
    }
    
    suspend fun getCurrentUser(): Result<UserEntity> {
        return try {
            val token = getToken().first() ?: return Result.failure(Exception("No token"))
            val userId = dataStore.data.first()[USER_ID_KEY] 
                ?: return Result.failure(Exception("No user ID"))
            
            // Try local first
            val localUser = userDao.getUserById(userId)
            if (localUser != null) {
                return Result.success(localUser)
            }
            
            // Fetch from API
            val userDto = authApi.getCurrentUser("Bearer $token")
            val userEntity = UserEntity(
                id = userDto.id,
                email = userDto.email,
                name = userDto.name,
                isPremium = userDto.isPremium,
                premiumExpiresAt = userDto.premiumExpiresAt,
                createdAt = userDto.createdAt,
                updatedAt = System.currentTimeMillis()
            )
            userDao.insertUser(userEntity)
            
            Result.success(userEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] != null
        }
    }
    
    suspend fun refreshToken(): Result<Unit> {
        return try {
            val refreshToken = dataStore.data.first()[REFRESH_TOKEN_KEY]
                ?: return Result.failure(Exception("No refresh token"))
            
            val response = authApi.refreshToken(mapOf("refresh_token" to refreshToken))
            
            dataStore.edit { preferences ->
                preferences[TOKEN_KEY] = response.session.accessToken
                preferences[REFRESH_TOKEN_KEY] = response.session.refreshToken
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
