package com.entryrank.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.entryrank.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    
    @Query("SELECT * FROM questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: String): QuestionEntity?
    
    @Query("SELECT * FROM questions WHERE subject = :subject ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomQuestionsBySubject(subject: String, limit: Int): List<QuestionEntity>
    
    @Query("""
        SELECT * FROM questions 
        WHERE subject = :subject 
        AND university = :university 
        ORDER BY RANDOM() 
        LIMIT :limit
    """)
    suspend fun getRandomQuestionsByUniversity(
        subject: String,
        university: String,
        limit: Int
    ): List<QuestionEntity>
    
    @Query("""
        SELECT * FROM questions 
        WHERE bookId = :bookId AND chapterId = :chapterId
        ORDER BY createdAt
    """)
    fun getQuestionsByChapterPaged(bookId: String, chapterId: String): PagingSource<Int, QuestionEntity>
    
    @Query("""
        SELECT * FROM questions 
        WHERE subject = :subject AND topic = :topic
        ORDER BY difficulty, RANDOM()
    """)
    fun getQuestionsByTopicPaged(subject: String, topic: String): PagingSource<Int, QuestionEntity>
    
    @Query("SELECT * FROM questions WHERE subject = :subject")
    fun getAllQuestionsBySubjectPaged(subject: String): PagingSource<Int, QuestionEntity>
    
    @Query("SELECT COUNT(*) FROM questions WHERE isDownloaded = 1")
    fun getDownloadedQuestionsCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM questions WHERE subject = :subject")
    suspend fun getQuestionCountBySubject(subject: String): Int
    
    @Query("""
        SELECT * FROM questions 
        WHERE id IN (:questionIds)
        ORDER BY 
            CASE 
                WHEN id = :questionIds[0] THEN 0
                WHEN id = :questionIds[1] THEN 1
                ELSE 2
            END
    """)
    suspend fun getQuestionsByIds(questionIds: List<String>): List<QuestionEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)
    
    @Update
    suspend fun updateQuestion(question: QuestionEntity)
    
    @Query("UPDATE questions SET isDownloaded = 1 WHERE bookId = :bookId AND chapterId = :chapterId")
    suspend fun markChapterQuestionsAsDownloaded(bookId: String, chapterId: String)
    
    @Query("DELETE FROM questions WHERE bookId = :bookId AND chapterId = :chapterId")
    suspend fun deleteQuestionsByChapter(bookId: String, chapterId: String)
    
    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()
    
    // Full-text search
    @Query("""
        SELECT * FROM questions 
        WHERE question LIKE '%' || :query || '%' 
        OR explanation LIKE '%' || :query || '%'
    """)
    fun searchQuestions(query: String): PagingSource<Int, QuestionEntity>
}
