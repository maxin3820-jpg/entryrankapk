package com.entryrank.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    indices = [Index(value = ["subject"])]
)
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subject: String,
    val year: String?,
    val publisher: String?,
    val totalChapters: Int = 0,
    val totalQuestions: Int = 0,
    val isDownloaded: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
