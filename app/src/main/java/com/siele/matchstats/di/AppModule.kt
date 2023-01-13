package com.siele.matchstats.di

import com.siele.matchstats.data.api.MatchStatsApi
import com.siele.matchstats.data.api.RetrofitInstance
import com.siele.matchstats.data.repository.MatchStatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideRepository(matchStatsApi: MatchStatsApi) = MatchStatsRepository(matchStatsApi)

}