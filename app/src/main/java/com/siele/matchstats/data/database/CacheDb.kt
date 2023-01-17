package com.siele.matchstats.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.fixtures.LeagueFixtures
import com.siele.matchstats.data.model.leagues.LeagueResponse

@Database(
    entities = [LeagueResponse::class, LeagueFixtures::class],
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