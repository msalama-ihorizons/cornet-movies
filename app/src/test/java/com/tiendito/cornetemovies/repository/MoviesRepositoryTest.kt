package com.tiendito.cornetemovies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiendito.bmisrmovies.api.Movie
import com.tiendito.bmisrmovies.api.MoviesResponse
import com.tiendito.cornetemovies.api.MoviesApis
import com.tiendito.cornetemovies.db.MoviesDatabase
import com.tiendito.cornetemovies.db.RecentSearchDao
import com.tiendito.cornetemovies.mock
import com.tiendito.cornetemovies.model.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.Response

/**
 * Created by Mohamed Salama on 9/8/2020.
 */

@RunWith(AndroidJUnit4::class)
class MoviesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesRepository: MoviesRepository

    private val service = Mockito.mock(MoviesApis::class.java)
    private val dao = Mockito.mock(RecentSearchDao::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(MoviesDatabase::class.java)
        Mockito.`when`(db.recentSearchDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        moviesRepository = MoviesRepository(service, dao)
    }


    @Test
    fun loadDiscoverMoviesTest() {
        val observer = mock<Observer<Resource<List<Movie>>>>()

        val moviesResponse = MoviesResponse(emptyList())

        val apiResponse = Response.success(moviesResponse)

        runBlocking {
            Mockito.`when`(service.getDiscoverMovies()).thenReturn(apiResponse)

            moviesRepository.loadDiscoverMovies().observeForever(observer)

            Mockito.verify(observer).onChanged(Resource.loading(null))
            Mockito.verify(observer).onChanged(Resource.complete(null))
            Mockito.verify(observer).onChanged(Resource.success(emptyList()))
        }
    }


}