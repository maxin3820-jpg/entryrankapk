package com.entryrank.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "universities")
data class UniversityEntity(
    @PrimaryKey val id: String,
    val name: String,
    val code: String, // NUST, FAST, GIKI, etc.
    val logoUrl: String?,
    val totalQuestions: Int = 0,
    val subjects: String, // JSON array of subjects
    val isActive: Boolean = true,
    val displayOrder: Int = 0
)
