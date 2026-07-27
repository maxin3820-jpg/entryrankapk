package com.entryrank.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.entryrank.data.local.entity.PracticeSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeSessionDao {
    
    @Query("SELECT * FROM practice_sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: String): PracticeSessionEntity?
    
    @Query("""
        SELECT * FROM practice_sessions 
        WHERE userId = :userId 
        ORDER BY completedAt DESC
    """)
    fun getUserSessionsPaged(userId: String): PagingSource<Int, PracticeSessionEntity>
    
    @Query("""
        SELECT * FROM practice_sessions 
        WHERE userId = :userId AND subject = :subject
        ORDER BY completedAt DESC
    """)
    fun getUserSessionsBySubjectPaged(userId: String, subject: String): PagingSource<Int, PracticeSessionEntity>
    
    @Query("""
        SELECT * FROM practice_sessions 
        WHERE userId = :userId AND isSynced = 0
    """)
    suspend fun getUnsyncedSessions(userId: String): List<PracticeSessionEntity>
    
    @Query("SELECT AVG(percentage) FROM practice_sessions WHERE userId = :userId AND subject = :subject")
    fun getAverageScoreBySubject(userId: String, subject: String): Flow<Double?>
    
    @Query("SELECT COUNT(*) FROM practice_sessions WHERE userId = :userId")
    fun getTotalSessionsCount(userId: String): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: PracticeSessionEntity)
    
    @Update
    suspend fun updateSession(session: PracticeSessionEntity)
    
    @Query("UPDATE practice_sessions SET isSynced = 1 WHERE id = :sessionId")
    suspend fun markSessionAsSynced(sessionId: String)
    
    @Query("DELETE FROM practice_sessions WHERE userId = :userId")
    suspend fun deleteUserSessions(userId: String)
}
