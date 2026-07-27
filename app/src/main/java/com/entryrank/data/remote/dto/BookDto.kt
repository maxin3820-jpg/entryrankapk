package com.entryrank.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: String,
    val title: String,
    val description: String? = null,
    @SerialName("subject_id")
    val subjectId: String,
    @SerialName("cover_image")
    val coverImage: String? = null,
    @SerialName("is_premium")
    val isPremium: Boolean = false,
    @SerialName("created_at")
    val createdAt: String
)

@Serializable
data class ChapterDto(
    val id: String,
    @SerialName("book_id")
    val bookId: String,
    val title: String,
    val description: String? = null,
    @SerialName("chapter_number")
    val chapterNumber: Int,
    @SerialName("question_count")
    val questionCount: Int = 0,
    @SerialName("created_at")
    val createdAt: String
)

@Serializable
data class BookWithChaptersDto(
    val book: BookDto,
    val chapters: List<ChapterDto>
)

@Serializable
data class BookListResponse(
    val data: List<BookDto>,
    val count: Int
)
