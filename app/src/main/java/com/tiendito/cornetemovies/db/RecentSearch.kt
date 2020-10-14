package com.tiendito.cornetemovies.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mohamed Salama on 10/13/2020.
 */

@Entity(tableName = "resent_searches_table")
data class RecentSearch(
    @PrimaryKey val keyword: String,
    val timeStamp: Long = System.currentTimeMillis()
)