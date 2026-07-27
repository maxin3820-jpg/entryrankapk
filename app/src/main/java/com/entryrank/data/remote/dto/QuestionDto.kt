package com.entryrank.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val id: String,
    val question: String,
    @SerialName("option_a")
    val optionA: String,
    @SerialName("option_b")
    val optionB: String,
    @SerialName("option_c")
    val optionC: String,
    @SerialName("option_d")
    val optionD: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    val explanation: String? = null,
    @SerialName("subject_id")
    val subjectId: String,
    @SerialName("topic_id")
    val topicId: String? = null,
    @SerialName("book_id")
    val bookId: String? = null,
    @SerialName("chapter_id")
    val chapterId: String? = null,
    @SerialName("university_id")
    val universityId: String? = null,
    val difficulty: Int? = null,
    @SerialName("created_at")
    val createdAt: String
)

@Serializable
data class QuestionListResponse(
    val data: List<QuestionDto>,
    val count: Int,
    val page: Int,
    val total: Int
)

@Serializable
data class QuestionFilterRequest(
    @SerialName("subject_id")
    val subjectId: String? = null,
    @SerialName("topic_id")
    val topicId: String? = null,
    @SerialName("book_id")
    val bookId: String? = null,
    @SerialName("chapter_id")
    val chapterId: String? = null,
    @SerialName("university_id")
    val universityId: String? = null,
    val difficulty: Int? = null,
    val limit: Int = 50,
    val offset: Int = 0,
    @SerialName("exclude_ids")
    val excludeIds: List<String>? = null
)
