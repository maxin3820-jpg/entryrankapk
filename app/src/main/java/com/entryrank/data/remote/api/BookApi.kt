package com.entryrank.data.remote.api

import com.entryrank.data.remote.dto.*
import retrofit2.http.*

interface BookApi {
    
    @GET("rest/v1/books")
    suspend fun getBooks(
        @Header("Authorization") token: String,
        @Query("subject_id") subjectId: String? = null,
        @Query("select") select: String = "*"
    ): List<BookDto>
    
    @GET("rest/v1/books")
    suspend fun getBookById(
        @Header("Authorization") token: String,
        @Query("id") bookId: String
    ): BookDto
    
    @GET("rest/v1/chapters")
    suspend fun getChaptersByBookId(
        @Header("Authorization") token: String,
        @Query("book_id") bookId: String,
        @Query("order") order: String = "chapter_number.asc"
    ): List<ChapterDto>
    
    @GET("rest/v1/chapters")
    suspend fun getChapterById(
        @Header("Authorization") token: String,
        @Query("id") chapterId: String
    ): ChapterDto
    
    @POST("rest/v1/rpc/get_book_with_chapters")
    suspend fun getBookWithChapters(
        @Header("Authorization") token: String,
        @Body params: Map<String, String>
    ): BookWithChaptersDto
}
