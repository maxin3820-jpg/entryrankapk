package com.entryrank.data.remote.api

import com.entryrank.data.remote.dto.*
import retrofit2.http.*

interface CommonApi {
    
    @GET("rest/v1/subjects")
    suspend fun getSubjects(
        @Header("Authorization") token: String,
        @Query("select") select: String = "*"
    ): List<SubjectDto>
    
    @GET("rest/v1/subjects")
    suspend fun getSubjectById(
        @Header("Authorization") token: String,
        @Query("id") subjectId: String
    ): SubjectDto
    
    @GET("rest/v1/topics")
    suspend fun getTopics(
        @Header("Authorization") token: String,
        @Query("subject_id") subjectId: String? = null
    ): List<TopicDto>
    
    @GET("rest/v1/topics")
    suspend fun getTopicById(
        @Header("Authorization") token: String,
        @Query("id") topicId: String
    ): TopicDto
    
    @GET("rest/v1/universities")
    suspend fun getUniversities(
        @Header("Authorization") token: String
    ): List<UniversityDto>
    
    @GET("rest/v1/universities")
    suspend fun getUniversityById(
        @Header("Authorization") token: String,
        @Query("id") universityId: String
    ): UniversityDto
}
