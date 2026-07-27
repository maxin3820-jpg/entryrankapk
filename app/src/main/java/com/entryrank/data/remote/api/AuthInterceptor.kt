package com.entryrank.data.remote.api

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Skip auth for login/signup requests
        if (originalRequest.url.encodedPath.contains("auth/v1/token") ||
            originalRequest.url.encodedPath.contains("auth/v1/signup")) {
            return chain.proceed(originalRequest)
        }
        
        val token = runBlocking {
            tokenProvider.getToken().first()
        }
        
        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .header("apikey", tokenProvider.getApiKey())
                .build()
        } else {
            originalRequest.newBuilder()
                .header("apikey", tokenProvider.getApiKey())
                .build()
        }
        
        return chain.proceed(newRequest)
    }
}

interface TokenProvider {
    suspend fun getToken(): kotlinx.coroutines.flow.Flow<String?>
    fun getApiKey(): String
}
