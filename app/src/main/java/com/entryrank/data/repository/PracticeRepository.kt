package com.entryrank.data.repository

import com.entryrank.data.local.dao.BookmarkDao
import com.entryrank.data.local.dao.PracticeSessionDao
import com.entryrank.data.local.dao.ProgressDao
import com.entryrank.data.local.entity.BookmarkEntity
import com.entryrank.data.local.entity.PracticeSessionEntity
import com.entryrank.data.local.entity.ProgressEntity
import com.entryrank.data.remote.api.PracticeApi
import com.entryrank.data.remote.dto.PracticeResultDto
import com.entryrank.data.remote.dto.SubmitPracticeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeRepository @Inject constructor(
    private val practiceApi: PracticeApi,
    private val practiceSessionDao: PracticeSessionDao,
    private val progressDao: ProgressDao,
    private val bookmarkDao: BookmarkDao,
    private val authRepository: AuthRepository
) {
    
    suspend fun submitPractice(
        questionIds: List<String>,
        answers: Map<String, String>,
        timeSpent: Int,
        subjectId: String? = null,
        topicId: String? = null,
        bookId: String? = null,
        chapterId: String? = null,
        universityId: String? = null
    ): Result<PracticeResultDto> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val request = SubmitPracticeRequest(
                subjectId = subjectId,
                topicId = topicId,
                bookId = bookId,
                chapterId = chapterId,
                universityId = universityId,
                questionIds = questionIds,
                answers = answers,
                timeSpent = timeSpent
            )
            
            val result = practiceApi.submitPractice("Bearer $token", request)
            
            // Save session locally
            val session = PracticeSessionEntity(
                id = result.session.id,
                userId = result.session.userId,
                subjectId = result.session.subjectId,
                topicId = result.session.topicId,
                bookId = result.session.bookId,
                chapterId = result.session.chapterId,
                universityId = result.session.universityId,
                totalQuestions = result.session.totalQuestions,
                correctAnswers = result.session.correctAnswers,
                wrongAnswers = result.session.wrongAnswers,
                skippedQuestions = result.session.skippedQuestions,
                timeSpent = result.session.timeSpent,
                score = result.session.score,
                createdAt = result.session.createdAt,
                completedAt = result.session.completedAt
            )
            
            practiceSessionDao.insertSession(session)
            
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getPracticeSessions(userId: String): Flow<List<PracticeSessionEntity>> {
        return practiceSessionDao.getUserSessions(userId)
    }
    
    suspend fun getPracticeSessionById(sessionId: String): Result<PracticeSessionEntity> {
        return try {
            val session = practiceSessionDao.getSessionById(sessionId)
            if (session != null) {
                Result.success(session)
            } else {
                Result.failure(Exception("Session not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getUserProgress(userId: String): Flow<List<ProgressEntity>> {
        return progressDao.getUserProgress(userId)
    }
    
    suspend fun syncProgress(userId: String): Result<Int> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val progressDto = practiceApi.getUserProgress("Bearer $token", userId)
            val progress = progressDto.map { dto ->
                ProgressEntity(
                    id = "${userId}_${dto.subjectId}",
                    userId = userId,
                    subjectId = dto.subjectId,
                    totalQuestionsAttempted = dto.totalQuestionsAttempted,
                    correctAnswers = dto.correctAnswers,
                    accuracy = dto.accuracy,
                    timeSpent = dto.timeSpent,
                    lastPracticed = dto.lastPracticed,
                    updatedAt = System.currentTimeMillis()
                )
            }
            
            progressDao.insertProgress(progress)
            Result.success(progress.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun bookmarkQuestion(userId: String, questionId: String): Result<Unit> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            practiceApi.bookmarkQuestion(
                "Bearer $token",
                mapOf("user_id" to userId, "question_id" to questionId)
            )
            
            val bookmark = BookmarkEntity(
                id = "${userId}_$questionId",
                userId = userId,
                questionId = questionId,
                createdAt = System.currentTimeMillis()
            )
            bookmarkDao.insertBookmark(bookmark)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun removeBookmark(userId: String, questionId: String): Result<Unit> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            practiceApi.removeBookmark("Bearer $token", questionId, userId)
            bookmarkDao.deleteBookmark("${userId}_$questionId")
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getBookmarks(userId: String): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getUserBookmarks(userId)
    }
}
