package com.tiendito.cornetemovies.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Mohamed Salama on 10/13/2020.
 */

@Database(entities = [RecentSearch::class], version = 3, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}