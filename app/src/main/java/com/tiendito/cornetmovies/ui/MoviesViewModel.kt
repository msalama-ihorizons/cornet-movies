package com.tiendito.cornetmovies.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.tiendito.cornetmovies.repository.MoviesRepository

class MoviesViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchKeywordLiveData = MutableLiveData<SearchLoad>()
    private var searchMoviesPage = 1

    var searchMoviesListLiveData = searchKeywordLiveData.switchMap { searchKeyWord ->
        moviesRepository.searchMovies(
            searchKeyWord.searchKeyword,
            searchKeyWord.pageNumber
        )
    }

    fun searchMovies(searchKeyword: String) {
        searchMoviesPage = 1;
        searchKeywordLiveData.value = SearchLoad(searchKeyword, searchMoviesPage)
    }

    fun loadNextPage() {
        searchKeywordLiveData.value?.let {
            if (it.searchKeyword.isNotBlank()) {
                searchKeywordLiveData.value = SearchLoad(it.searchKeyword, searchMoviesPage ++)
            }
        }
    }

    data class SearchLoad(val searchKeyword: String, val pageNumber: Int = 1)

    val discoverMoviesListLiveData = moviesRepository.loadDiscoverMovies()

    val recentSearchedLiveDate = moviesRepository.loadRecentSearch()
}