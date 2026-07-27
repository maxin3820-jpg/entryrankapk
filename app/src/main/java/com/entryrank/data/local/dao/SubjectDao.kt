package com.entryrank.data.local.dao

import androidx.room.*
import com.entryrank.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    
    @Query("SELECT * FROM subjects WHERE id = :subjectId")
    suspend fun getSubjectById(subjectId: String): SubjectEntity?
    
    @Query("SELECT * FROM subjects ORDER BY displayOrder, name")
    fun getAllSubjects(): Flow<List<SubjectEntity>>
    
    @Query("SELECT * FROM subjects WHERE `group` = :group ORDER BY displayOrder, name")
    fun getSubjectsByGroup(group: String): Flow<List<SubjectEntity>>
    
    @Query("SELECT * FROM subjects WHERE code = :code")
    suspend fun getSubjectByCode(code: String): SubjectEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)
    
    @Update
    suspend fun updateSubject(subject: SubjectEntity)
    
    @Query("DELETE FROM subjects")
    suspend fun deleteAllSubjects()
}
