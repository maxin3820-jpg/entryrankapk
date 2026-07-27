package com.entryrank.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val name: String,
    val phone: String?,
    val university: String,
    val group: String, // Engineering, Medical, Computer Science
    val targetUniversity: String,
    val isPremium: Boolean = false,
    val subscriptionType: String?, // monthly, yearly
    val subscriptionExpiresAt: Long?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
