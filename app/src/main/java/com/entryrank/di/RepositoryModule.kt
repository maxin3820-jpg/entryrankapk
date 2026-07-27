package com.entryrank.di

import com.entryrank.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideTokenProvider(authRepository: AuthRepository): com.entryrank.data.remote.api.TokenProvider {
        return authRepository
    }
}
