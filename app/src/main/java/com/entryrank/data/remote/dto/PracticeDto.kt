package com.entryrank.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PracticeSessionDto(
    val id: String,
    @SerialName("user_id")
    val userId: String,
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
    @SerialName("total_questions")
    val totalQuestions: Int,
    @SerialName("correct_answers")
    val correctAnswers: Int,
    @SerialName("wrong_answers")
    val wrongAnswers: Int,
    @SerialName("skipped_questions")
    val skippedQuestions: Int,
    @SerialName("time_spent")
    val timeSpent: Int,
    val score: Float,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("completed_at")
    val completedAt: String? = null
)

@Serializable
data class SubmitPracticeRequest(
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
    @SerialName("question_ids")
    val questionIds: List<String>,
    val answers: Map<String, String>,
    @SerialName("time_spent")
    val timeSpent: Int
)

@Serializable
data class PracticeResultDto(
    val session: PracticeSessionDto,
    val details: List<QuestionResultDto>
)

@Serializable
data class QuestionResultDto(
    @SerialName("question_id")
    val questionId: String,
    val question: String,
    @SerialName("user_answer")
    val userAnswer: String?,
    @SerialName("correct_answer")
    val correctAnswer: String,
    @SerialName("is_correct")
    val isCorrect: Boolean,
    val explanation: String? = null
)

@Serializable
data class ProgressDto(
    @SerialName("subject_id")
    val subjectId: String,
    @SerialName("subject_name")
    val subjectName: String,
    @SerialName("total_questions_attempted")
    val totalQuestionsAttempted: Int,
    @SerialName("correct_answers")
    val correctAnswers: Int,
    val accuracy: Float,
    @SerialName("time_spent")
    val timeSpent: Int,
    @SerialName("last_practiced")
    val lastPracticed: String?
)
