package com.entryrank.data.remote.api

import com.entryrank.data.remote.dto.*
import retrofit2.http.*

interface QuestionApi {
    
    @GET("rest/v1/questions")
    suspend fun getQuestions(
        @Header("Authorization") token: String,
        @Query("subject_id") subjectId: String? = null,
        @Query("topic_id") topicId: String? = null,
        @Query("book_id") bookId: String? = null,
        @Query("chapter_id") chapterId: String? = null,
        @Query("university_id") universityId: String? = null,
        @Query("difficulty") difficulty: Int? = null,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): List<QuestionDto>
    
    @GET("rest/v1/questions")
    suspend fun getQuestionById(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): QuestionDto
    
    @POST("rest/v1/rpc/get_random_questions")
    suspend fun getRandomQuestions(
        @Header("Authorization") token: String,
        @Body params: QuestionFilterRequest
    ): List<QuestionDto>
    
    @GET("rest/v1/questions")
    suspend fun searchQuestions(
        @Header("Authorization") token: String,
        @Query("question") search: String,
        @Query("limit") limit: Int = 20
    ): List<QuestionDto>
}
