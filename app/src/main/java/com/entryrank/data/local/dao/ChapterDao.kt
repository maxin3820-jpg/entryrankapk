package com.entryrank.data.local.dao

import androidx.room.*
import com.entryrank.data.local.entity.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    
    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    suspend fun getChapterById(chapterId: String): ChapterEntity?
    
    @Query("SELECT * FROM chapters WHERE bookId = :bookId ORDER BY chapterNumber")
    fun getChaptersByBook(bookId: String): Flow<List<ChapterEntity>>
    
    @Query("SELECT * FROM chapters WHERE bookId = :bookId AND isDownloaded = 1")
    fun getDownloadedChaptersByBook(bookId: String): Flow<List<ChapterEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: ChapterEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)
    
    @Update
    suspend fun updateChapter(chapter: ChapterEntity)
    
    @Query("UPDATE chapters SET isDownloaded = 1 WHERE id = :chapterId")
    suspend fun markChapterAsDownloaded(chapterId: String)
    
    @Query("DELETE FROM chapters WHERE bookId = :bookId")
    suspend fun deleteChaptersByBook(bookId: String)
}
