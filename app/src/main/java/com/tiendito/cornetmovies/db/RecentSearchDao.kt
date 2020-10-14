package com.tiendito.cornetmovies.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(recentSearch: RecentSearch)

    @Query("SELECT * from resent_searches_table order by timeStamp DESC")
    fun loadRecentSearches(): LiveData<List<RecentSearch>>

}