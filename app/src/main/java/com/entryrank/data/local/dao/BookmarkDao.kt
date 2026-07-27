package com.entryrank.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.entryrank.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    
    @Query("SELECT * FROM bookmarks WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserBookmarksPaged(userId: String): PagingSource<Int, BookmarkEntity>
    
    @Query("SELECT * FROM bookmarks WHERE userId = :userId AND questionId = :questionId")
    suspend fun getBookmark(userId: String, questionId: String): BookmarkEntity?
    
    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE userId = :userId AND questionId = :questionId)")
    fun isBookmarked(userId: String, questionId: String): Flow<Boolean>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)
    
    @Query("DELETE FROM bookmarks WHERE userId = :userId AND questionId = :questionId")
    suspend fun deleteBookmark(userId: String, questionId: String)
    
    @Query("DELETE FROM bookmarks WHERE userId = :userId")
    suspend fun deleteUserBookmarks(userId: String)
}
