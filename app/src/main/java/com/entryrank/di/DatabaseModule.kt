package com.entryrank.di

import android.content.Context
import androidx.room.Room
import com.entryrank.data.local.dao.*
import com.entryrank.data.local.database.EntryRankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): EntryRankDatabase {
        return Room.databaseBuilder(
            context,
            EntryRankDatabase::class.java,
            "entryrank_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideUserDao(database: EntryRankDatabase): UserDao {
        return database.userDao()
    }
    
    @Provides
    fun provideQuestionDao(database: EntryRankDatabase): QuestionDao {
        return database.questionDao()
    }
    
    @Provides
    fun provideBookDao(database: EntryRankDatabase): BookDao {
        return database.bookDao()
    }
    
    @Provides
    fun provideChapterDao(database: EntryRankDatabase): ChapterDao {
        return database.chapterDao()
    }
    
    @Provides
    fun provideSubjectDao(database: EntryRankDatabase): SubjectDao {
        return database.subjectDao()
    }
    
    @Provides
    fun provideTopicDao(database: EntryRankDatabase): TopicDao {
        return database.topicDao()
    }
    
    @Provides
    fun provideUniversityDao(database: EntryRankDatabase): UniversityDao {
        return database.universityDao()
    }
    
    @Provides
    fun providePracticeSessionDao(database: EntryRankDatabase): PracticeSessionDao {
        return database.practiceSessionDao()
    }
    
    @Provides
    fun provideProgressDao(database: EntryRankDatabase): ProgressDao {
        return database.progressDao()
    }
    
    @Provides
    fun provideBookmarkDao(database: EntryRankDatabase): BookmarkDao {
        return database.bookmarkDao()
    }
}
