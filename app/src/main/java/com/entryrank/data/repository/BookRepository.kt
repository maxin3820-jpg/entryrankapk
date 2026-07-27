package com.entryrank.data.repository

import com.entryrank.data.local.dao.BookDao
import com.entryrank.data.local.dao.ChapterDao
import com.entryrank.data.local.entity.BookEntity
import com.entryrank.data.local.entity.ChapterEntity
import com.entryrank.data.remote.api.BookApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao,
    private val authRepository: AuthRepository
) {
    
    fun getBooks(subjectId: String? = null): Flow<List<BookEntity>> {
        return bookDao.getBooks(subjectId)
    }
    
    suspend fun syncBooks(): Result<Int> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val booksDto = bookApi.getBooks("Bearer $token")
            val books = booksDto.map { dto ->
                BookEntity(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    subjectId = dto.subjectId,
                    coverImage = dto.coverImage,
                    isPremium = dto.isPremium,
                    createdAt = dto.createdAt
                )
            }
            
            bookDao.insertBooks(books)
            Result.success(books.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getChaptersByBookId(bookId: String): Flow<List<ChapterEntity>> {
        return chapterDao.getChaptersByBookId(bookId)
    }
    
    suspend fun syncChapters(bookId: String): Result<Int> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val chaptersDto = bookApi.getChaptersByBookId("Bearer $token", bookId)
            val chapters = chaptersDto.map { dto ->
                ChapterEntity(
                    id = dto.id,
                    bookId = dto.bookId,
                    title = dto.title,
                    description = dto.description,
                    chapterNumber = dto.chapterNumber,
                    questionCount = dto.questionCount,
                    createdAt = dto.createdAt
                )
            }
            
            chapterDao.insertChapters(chapters)
            Result.success(chapters.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getBookById(bookId: String): Result<BookEntity> {
        return try {
            val localBook = bookDao.getBookById(bookId)
            if (localBook != null) {
                return Result.success(localBook)
            }
            
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val dto = bookApi.getBookById("Bearer $token", bookId)
            val book = BookEntity(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                subjectId = dto.subjectId,
                coverImage = dto.coverImage,
                isPremium = dto.isPremium,
                createdAt = dto.createdAt
            )
            
            bookDao.insertBooks(listOf(book))
            Result.success(book)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
