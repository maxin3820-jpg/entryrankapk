package com.entryrank.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.entryrank.data.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookEntity?
    
    @Query("SELECT * FROM books WHERE subject = :subject ORDER BY title")
    fun getBooksBySubject(subject: String): Flow<List<BookEntity>>
    
    @Query("SELECT * FROM books ORDER BY subject, title")
    fun getAllBooksPaged(): PagingSource<Int, BookEntity>
    
    @Query("SELECT * FROM books WHERE subject = :subject")
    fun getBooksBySubjectPaged(subject: String): PagingSource<Int, BookEntity>
    
    @Query("SELECT * FROM books WHERE isDownloaded = 1")
    fun getDownloadedBooks(): Flow<List<BookEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)
    
    @Update
    suspend fun updateBook(book: BookEntity)
    
    @Query("UPDATE books SET isDownloaded = 1 WHERE id = :bookId")
    suspend fun markBookAsDownloaded(bookId: String)
    
    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBook(bookId: String)
    
    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}
