package com.entryrank.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectDto(
    val id: String,
    val name: String,
    val description: String? = null,
    @SerialName("icon_name")
    val iconName: String? = null,
    @SerialName("question_count")
    val questionCount: Int = 0
)

@Serializable
data class TopicDto(
    val id: String,
    @SerialName("subject_id")
    val subjectId: String,
    val name: String,
    val description: String? = null,
    @SerialName("question_count")
    val questionCount: Int = 0
)

@Serializable
data class UniversityDto(
    val id: String,
    val name: String,
    @SerialName("short_name")
    val shortName: String,
    val description: String? = null,
    @SerialName("logo_url")
    val logoUrl: String? = null
)

@Serializable
data class ApiError(
    val error: String,
    val message: String,
    val code: Int? = null
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
    val message: String? = null
)
