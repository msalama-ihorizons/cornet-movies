package com.tiendito.cornetemovies.ui.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.tiendito.cornetemovies.repository.MoviesRepository
import com.tiendito.cornetemovies.ui.MoviesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Mohamed Salama on 9/8/2020.
 */
class MovieDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesViewModel
    private val repository = Mockito.mock(MoviesRepository::class.java)
    private val savedStateHandle = Mockito.mock(SavedStateHandle::class.java)

    @Before
    fun init() {
        // need to init after instant executor rule is established.
        viewModel = MoviesViewModel(repository, savedStateHandle)
    }

    @Test
    fun testSearchMovieDetails() {
        viewModel.searchMovies("nothing")
        Mockito.verify(repository, Mockito.never())
            .searchMovies("", 1)
    }


}