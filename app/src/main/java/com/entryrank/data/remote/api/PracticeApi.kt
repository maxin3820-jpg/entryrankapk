package com.entryrank.data.remote.api

import com.entryrank.data.remote.dto.*
import retrofit2.http.*

interface PracticeApi {
    
    @POST("rest/v1/practice_sessions")
    suspend fun createPracticeSession(
        @Header("Authorization") token: String,
        @Body request: SubmitPracticeRequest
    ): PracticeSessionDto
    
    @GET("rest/v1/practice_sessions")
    suspend fun getPracticeSessions(
        @Header("Authorization") token: String,
        @Query("user_id") userId: String,
        @Query("order") order: String = "created_at.desc",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): List<PracticeSessionDto>
    
    @GET("rest/v1/practice_sessions")
    suspend fun getPracticeSessionById(
        @Header("Authorization") token: String,
        @Query("id") sessionId: String
    ): PracticeSessionDto
    
    @POST("rest/v1/rpc/submit_practice")
    suspend fun submitPractice(
        @Header("Authorization") token: String,
        @Body request: SubmitPracticeRequest
    ): PracticeResultDto
    
    @GET("rest/v1/rpc/get_user_progress")
    suspend fun getUserProgress(
        @Header("Authorization") token: String,
        @Query("user_id") userId: String
    ): List<ProgressDto>
    
    @POST("rest/v1/bookmarks")
    suspend fun bookmarkQuestion(
        @Header("Authorization") token: String,
        @Body bookmark: Map<String, String>
    )
    
    @DELETE("rest/v1/bookmarks")
    suspend fun removeBookmark(
        @Header("Authorization") token: String,
        @Query("question_id") questionId: String,
        @Query("user_id") userId: String
    )
    
    @GET("rest/v1/bookmarks")
    suspend fun getBookmarks(
        @Header("Authorization") token: String,
        @Query("user_id") userId: String
    ): List<String>
}
