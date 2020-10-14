package com.tiendito.cornetmovies.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiendito.cornetmovies.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesDatabase: MoviesDatabase

    @Before
    fun initDB() {

        moviesDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java
        )
            .setTransactionExecutor(Dispatchers.IO.asExecutor())
            .setQueryExecutor(Dispatchers.IO.asExecutor())
            .build()
    }

    @After
    fun closeDb() {
        moviesDatabase.close()
    }

    @Test
    fun insertMovieTest() {
        val recentSearch = createRecentSearch()
        runBlocking {
            moviesDatabase.recentSearchDao().insertRecentSearch(recentSearch)
            val recentSearches = moviesDatabase.recentSearchDao().loadRecentSearches()
            assert(recentSearches.getOrAwaitValue().isNotEmpty())
        }
    }



    private fun createRecentSearch(): RecentSearch {
        return RecentSearch(
            keyword = "man",
            timeStamp = 38943498
        )
    }
}