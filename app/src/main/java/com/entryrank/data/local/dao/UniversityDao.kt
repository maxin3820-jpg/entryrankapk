package com.entryrank.data.local.dao

import androidx.room.*
import com.entryrank.data.local.entity.UniversityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDao {
    
    @Query("SELECT * FROM universities WHERE id = :universityId")
    suspend fun getUniversityById(universityId: String): UniversityEntity?
    
    @Query("SELECT * FROM universities WHERE isActive = 1 ORDER BY displayOrder, name")
    fun getAllUniversities(): Flow<List<UniversityEntity>>
    
    @Query("SELECT * FROM universities WHERE code = :code")
    suspend fun getUniversityByCode(code: String): UniversityEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversity(university: UniversityEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversities(universities: List<UniversityEntity>)
    
    @Update
    suspend fun updateUniversity(university: UniversityEntity)
    
    @Query("DELETE FROM universities")
    suspend fun deleteAllUniversities()
}
