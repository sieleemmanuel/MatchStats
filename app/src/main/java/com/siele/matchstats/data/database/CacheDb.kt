package com.siele.matchstats.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.siele.matchstats.data.model.fixtures.LeagueFixtures
import com.siele.matchstats.data.model.fixtures.CurrentLeagueRound
import com.siele.matchstats.data.model.fixtures.LeagueRounds
import com.siele.matchstats.data.model.leagues.LeagueResponse
import com.siele.matchstats.data.model.standings.LeagueStanding

@Database(
    entities = [
        LeagueResponse::class,
        LeagueFixtures::class,
        CurrentLeagueRound::class,
        LeagueRounds::class,
        LeagueStanding::class ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(ListsConverter::class)
abstract class CacheDb : RoomDatabase() {
    abstract val cacheDao: CacheDao

    companion object {
        @Volatile
        var INSTANCE: CacheDb? = null

        fun getInstance(context: Context): CacheDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, CacheDb::class.java, "cache_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}