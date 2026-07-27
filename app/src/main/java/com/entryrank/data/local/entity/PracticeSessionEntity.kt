package com.entryrank.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.entryrank.data.local.database.Converters

@Entity(
    tableName = "practice_sessions",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["subject"]),
        Index(value = ["university"]),
        Index(value = ["completedAt"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@TypeConverters(Converters::class)
data class PracticeSessionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val university: String?,
    val subject: String,
    val mode: String, // practice, test, topic
    val score: Int,
    val total: Int,
    val percentage: Double,
    val totalTimeSeconds: Int,
    val avgTimeSeconds: Double,
    val answers: Map<String, String>, // questionId -> selectedAnswer
    val questionIds: List<String>,
    val timePerQuestion: Map<String, Int>, // questionId -> seconds
    val questionSource: String, // book, university, topic
    val bookId: String?,
    val chapterId: String?,
    val completedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)
