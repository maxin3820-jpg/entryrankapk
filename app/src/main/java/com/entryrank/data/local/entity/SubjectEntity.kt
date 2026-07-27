package com.entryrank.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val code: String, // physics, chemistry, mathematics, etc.
    val iconUrl: String?,
    val totalQuestions: Int = 0,
    val totalTopics: Int = 0,
    val group: String, // Engineering, Medical, Computer Science
    val displayOrder: Int = 0
)
