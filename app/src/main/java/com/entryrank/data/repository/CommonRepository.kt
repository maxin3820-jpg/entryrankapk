package com.entryrank.data.repository

import com.entryrank.data.local.dao.SubjectDao
import com.entryrank.data.local.dao.TopicDao
import com.entryrank.data.local.dao.UniversityDao
import com.entryrank.data.local.entity.SubjectEntity
import com.entryrank.data.local.entity.TopicEntity
import com.entryrank.data.local.entity.UniversityEntity
import com.entryrank.data.remote.api.CommonApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonRepository @Inject constructor(
    private val commonApi: CommonApi,
    private val subjectDao: SubjectDao,
    private val topicDao: TopicDao,
    private val universityDao: UniversityDao,
    private val authRepository: AuthRepository
) {
    
    fun getSubjects(): Flow<List<SubjectEntity>> {
        return subjectDao.getAllSubjects()
    }
    
    suspend fun syncSubjects(): Result<Int> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val subjectsDto = commonApi.getSubjects("Bearer $token")
            val subjects = subjectsDto.map { dto ->
                SubjectEntity(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description,
                    iconName = dto.iconName,
                    questionCount = dto.questionCount
                )
            }
            
            subjectDao.insertSubjects(subjects)
            Result.success(subjects.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getTopics(subjectId: String? = null): Flow<List<TopicEntity>> {
        return if (subjectId != null) {
            topicDao.getTopicsBySubjectId(subjectId)
        } else {
            topicDao.getAllTopics()
        }
    }
    
    suspend fun syncTopics(subjectId: String? = null): Result<Int> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val topicsDto = commonApi.getTopics("Bearer $token", subjectId)
            val topics = topicsDto.map { dto ->
                TopicEntity(
                    id = dto.id,
                    subjectId = dto.subjectId,
                    name = dto.name,
                    description = dto.description,
                    questionCount = dto.questionCount
                )
            }
            
            topicDao.insertTopics(topics)
            Result.success(topics.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getUniversities(): Flow<List<UniversityEntity>> {
        return universityDao.getAllUniversities()
    }
    
    suspend fun syncUniversities(): Result<Int> {
        return try {
            val token = authRepository.getToken().first()
                ?: return Result.failure(Exception("No token"))
            
            val universitiesDto = commonApi.getUniversities("Bearer $token")
            val universities = universitiesDto.map { dto ->
                UniversityEntity(
                    id = dto.id,
                    name = dto.name,
                    shortName = dto.shortName,
                    description = dto.description,
                    logoUrl = dto.logoUrl
                )
            }
            
            universityDao.insertUniversities(universities)
            Result.success(universities.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
