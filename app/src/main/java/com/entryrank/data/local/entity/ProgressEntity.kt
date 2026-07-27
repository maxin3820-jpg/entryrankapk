package com.entryrank.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "progress",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["subject"]),
        Index(value = ["userId", "subject", "topic"], unique = true)
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
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val subject: String,
    val topic: String?,
    val totalAttempted: Int = 0,
    val totalCorrect: Int = 0,
    val totalIncorrect: Int = 0,
    val accuracy: Double = 0.0,
    val lastPracticeAt: Long?,
    val updatedAt: Long = System.currentTimeMillis()
)
