package com.entryrank.data.repository

import com.entryrank.data.demo.DemoData
import com.entryrank.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Demo Repository - Provides data without backend
 * Perfect for testing and demonstrations!
 */
@Singleton
class DemoRepository @Inject constructor() {
    
    private var isDemoMode = false
    
    fun enableDemoMode() {
        isDemoMode = true
    }
    
    fun isDemoMode(): Boolean = isDemoMode
    
    // Get demo user
    suspend fun getDemoUser(): Result<UserEntity> {
        return Result.success(DemoData.demoUser)
    }
    
    // Get demo subjects
    fun getDemoSubjects(): Flow<List<SubjectEntity>> = flow {
        emit(DemoData.demoSubjects)
    }
    
    // Get demo books
    fun getDemoBooks(): Flow<List<BookEntity>> = flow {
        emit(DemoData.demoBooks)
    }
    
    // Get demo questions for a subject
    suspend fun getDemoQuestions(
        subjectId: String,
        count: Int = 50
    ): Result<List<QuestionEntity>> {
        val questions = DemoData.generateDemoQuestions(subjectId, count)
        return Result.success(questions)
    }
    
    // Get demo progress
    fun getDemoProgress(userId: String): Flow<List<ProgressEntity>> = flow {
        emit(DemoData.demoProgress)
    }
    
    // Get demo practice sessions
    fun getDemoPracticeSessions(userId: String): Flow<List<PracticeSessionEntity>> = flow {
        emit(DemoData.demoPracticeSessions)
    }
    
    // Save demo practice session
    suspend fun saveDemoPracticeSession(session: PracticeSessionEntity): Result<String> {
        // Just return success - no actual saving needed in demo mode
        return Result.success(session.id)
    }
    
    // Demo login (always succeeds)
    suspend fun demoLogin(email: String, password: String): Result<UserEntity> {
        return Result.success(DemoData.demoUser.copy(email = email))
    }
    
    // Demo signup (always succeeds)
    suspend fun demoSignup(name: String, email: String, password: String): Result<UserEntity> {
        return Result.success(DemoData.demoUser.copy(name = name, email = email))
    }
}
