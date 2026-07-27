package com.entryrank.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.entryrank.data.local.dao.*
import com.entryrank.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        BookEntity::class,
        ChapterEntity::class,
        QuestionEntity::class,
        SubjectEntity::class,
        TopicEntity::class,
        UniversityEntity::class,
        PracticeSessionEntity::class,
        ProgressEntity::class,
        BookmarkEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class EntryRankDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
    abstract fun chapterDao(): ChapterDao
    abstract fun questionDao(): QuestionDao
    abstract fun subjectDao(): SubjectDao
    abstract fun topicDao(): TopicDao
    abstract fun universityDao(): UniversityDao
    abstract fun practiceSessionDao(): PracticeSessionDao
    abstract fun progressDao(): ProgressDao
    abstract fun bookmarkDao(): BookmarkDao
    
    companion object {
        const val DATABASE_NAME = "entryrank_db"
    }
}
