package com.entryrank.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    indices = [
        Index(value = ["bookId"]),
        Index(value = ["chapterId"]),
        Index(value = ["subject"]),
        Index(value = ["difficulty"]),
        Index(value = ["university"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ChapterEntity::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class QuestionEntity(
    @PrimaryKey val id: String,
    val bookId: String?,
    val chapterId: String?,
    val sourceId: String?,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val optionE: String?,
    val correctAnswer: String,
    val explanation: String?,
    val subject: String,
    val difficulty: String = "unrated", // unrated, easy, medium, hard
    val university: String?,
    val topic: String?,
    val isDownloaded: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
