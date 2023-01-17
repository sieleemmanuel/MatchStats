package com.siele.matchstats.di

import android.content.Context
import com.siele.matchstats.data.api.MatchStatsApi
import com.siele.matchstats.data.api.RetrofitInstance
import com.siele.matchstats.data.database.CacheDao
import com.siele.matchstats.data.database.CacheDb
import com.siele.matchstats.data.repository.MatchStatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService():MatchStatsApi = RetrofitInstance.retrofit.create(MatchStatsApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(matchStatsApi: MatchStatsApi, cacheDao: CacheDao) = MatchStatsRepository(matchStatsApi, cacheDao)

    @Provides
    @Singleton
    fun provideCacheDb(@ApplicationContext context: Context) = CacheDb.getInstance(context)

    @Provides
    @Singleton
    fun provideCacheDao(cacheDb: CacheDb) = cacheDb.cacheDao

}