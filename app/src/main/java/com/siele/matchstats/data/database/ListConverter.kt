package com.siele.matchstats.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.siele.matchstats.data.model.fixtures.FixtureInfo
import com.siele.matchstats.data.model.leagues.Country
import com.siele.matchstats.data.model.leagues.League
import com.siele.matchstats.data.model.leagues.Season
import com.siele.matchstats.data.model.standings.Standing
import com.siele.matchstats.data.model.stats.assists.SeasonTopAssists
import com.siele.matchstats.data.model.stats.scorers.SeasonTopScorers
import com.siele.matchstats.data.model.teams.SeasonTeams

class ListsConverter {
    // seasons
    @TypeConverter
    fun fromString(value: String): List<Season> {
        val type = object : TypeToken<List<Season>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<Season>): String {
        val type = object : TypeToken<List<Season>>() {}.type
        return Gson().toJson(list, type)
    }

    // country
    @TypeConverter
    fun fromCountryString(value: String): Country =
        (Gson().fromJson(value, Country::class.java) as Country)

    @TypeConverter
    fun toCountry(value: Country): String = Gson().toJson(value)

    // league
    @TypeConverter
    fun fromLeagueString(value: String): League =
        (Gson().fromJson(value, League::class.java) as League)

    @TypeConverter
    fun toLeague(value: League): String = Gson().toJson(value)

    // fixtures
    @TypeConverter
    fun fromFixtureInfoString(value: String): List<FixtureInfo> {
        val type = object : TypeToken<List<FixtureInfo>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromFixtureInfoList(list: List<FixtureInfo>): String {
        val type = object : TypeToken<List<FixtureInfo>>() {}.type
        return Gson().toJson(list, type)
    }

    /*
        @TypeConverter
        fun fromFixtureString(value: String): Fixture =
            (Gson().fromJson(value, Fixture::class.java) as Fixture)
        @TypeConverter
        fun toFixture(value: Fixture?): String = Gson().toJson(value)

        @TypeConverter
        fun fromGoalsString(value: String): Goals =
            (Gson().fromJson(value, Goals::class.java) as Goals)
        @TypeConverter
        fun toGoals(value: Goals?): String = Gson().toJson(value)

        @TypeConverter
        fun fromFixtureLeagueString(value: String): FixtureLeague =
            (Gson().fromJson(value, FixtureLeague::class.java) as FixtureLeague)
        @TypeConverter
        fun toFixtureLeague(value: FixtureLeague?): String = Gson().toJson(value)

        @TypeConverter
        fun fromScoreString(value: String): Score =
            (Gson().fromJson(value, Score::class.java) as Score)
        @TypeConverter
        fun toScore(value: Score?): String = Gson().toJson(value)

        @TypeConverter
        fun fromTeamsString(value: String): Teams =
            (Gson().fromJson(value, Teams::class.java) as Teams)
        @TypeConverter
        fun toTeams(value: Teams?): String = Gson().toJson(value)

        @TypeConverter
        fun fromCoverageString(value: String): Coverage =
            (Gson().fromJson(value, Coverage::class.java) as Coverage)
        @TypeConverter
        fun toCoverage(value: Coverage?): String = Gson().toJson(value)

        @TypeConverter
        fun fromFixturesString(value: String): Fixtures =
            (Gson().fromJson(value, Fixtures::class.java) as Fixtures)
        @TypeConverter
        fun toFixtures(value: Fixtures?): String = Gson().toJson(value)
    */
// rounds
    @TypeConverter
    fun fromListString(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toListString(list: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(list, type)
    }

    // standings
    @TypeConverter
    fun fromStandingsString(value: String): List<List<Standing>> {
        val type = object : TypeToken<List<List<Standing>>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toStandingsString(list: List<List<Standing>>): String {
        val type = object : TypeToken<List<List<Standing>>>() {}.type
        return Gson().toJson(list, type)
    }

// scorers

    @TypeConverter
    fun fromListScorers(value: String): List<SeasonTopScorers> {
        val type = object : TypeToken<List<SeasonTopScorers>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toScoresString(list: List<SeasonTopScorers>): String {
        val type = object : TypeToken<List<SeasonTopScorers>>() {}.type
        return Gson().toJson(list, type)
    }

    // assists

    @TypeConverter
    fun fromListAssists(value: String): List<SeasonTopAssists> {
        val type = object : TypeToken<List<SeasonTopAssists>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toAssistsString(list: List<SeasonTopAssists>): String {
        val type = object : TypeToken<List<SeasonTopAssists>>() {}.type
        return Gson().toJson(list, type)
    }

    // Teams
    @TypeConverter
    fun fromListTeams(value: String): List<SeasonTeams> {
        val type = object : TypeToken<List<SeasonTeams>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toTeamsString(list: List<SeasonTeams>): String {
        val type = object : TypeToken<List<SeasonTeams>>() {}.type
        return Gson().toJson(list, type)
    }
}

