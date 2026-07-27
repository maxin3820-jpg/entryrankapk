package com.entryrank.data.local.dao

import androidx.room.*
import com.entryrank.data.local.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND subject = :subject AND topic IS NULL")
    fun getSubjectProgress(userId: String, subject: String): Flow<ProgressEntity?>
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND subject = :subject AND topic = :topic")
    fun getTopicProgress(userId: String, subject: String, topic: String): Flow<ProgressEntity?>
    
    @Query("SELECT * FROM progress WHERE userId = :userId ORDER BY subject, topic")
    fun getAllUserProgress(userId: String): Flow<List<ProgressEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ProgressEntity)
    
    @Update
    suspend fun updateProgress(progress: ProgressEntity)
    
    @Query("""
        UPDATE progress 
        SET totalAttempted = totalAttempted + :attempted,
            totalCorrect = totalCorrect + :correct,
            totalIncorrect = totalIncorrect + :incorrect,
            accuracy = CAST(totalCorrect + :correct AS REAL) / CAST(totalAttempted + :attempted AS REAL) * 100,
            lastPracticeAt = :timestamp,
            updatedAt = :timestamp
        WHERE userId = :userId AND subject = :subject AND topic IS NULL
    """)
    suspend fun incrementSubjectProgress(
        userId: String,
        subject: String,
        attempted: Int,
        correct: Int,
        incorrect: Int,
        timestamp: Long
    )
    
    @Query("""
        UPDATE progress 
        SET totalAttempted = totalAttempted + :attempted,
            totalCorrect = totalCorrect + :correct,
            totalIncorrect = totalIncorrect + :incorrect,
            accuracy = CAST(totalCorrect + :correct AS REAL) / CAST(totalAttempted + :attempted AS REAL) * 100,
            lastPracticeAt = :timestamp,
            updatedAt = :timestamp
        WHERE userId = :userId AND subject = :subject AND topic = :topic
    """)
    suspend fun incrementTopicProgress(
        userId: String,
        subject: String,
        topic: String,
        attempted: Int,
        correct: Int,
        incorrect: Int,
        timestamp: Long
    )
    
    @Query("DELETE FROM progress WHERE userId = :userId")
    suspend fun deleteUserProgress(userId: String)
}
