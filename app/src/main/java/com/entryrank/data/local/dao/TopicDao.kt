package com.entryrank.data.local.dao

import androidx.room.*
import com.entryrank.data.local.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    
    @Query("SELECT * FROM topics WHERE id = :topicId")
    suspend fun getTopicById(topicId: String): TopicEntity?
    
    @Query("SELECT * FROM topics WHERE subjectId = :subjectId ORDER BY displayOrder, name")
    fun getTopicsBySubject(subjectId: String): Flow<List<TopicEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: TopicEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<TopicEntity>)
    
    @Update
    suspend fun updateTopic(topic: TopicEntity)
    
    @Query("DELETE FROM topics WHERE subjectId = :subjectId")
    suspend fun deleteTopicsBySubject(subjectId: String)
}
