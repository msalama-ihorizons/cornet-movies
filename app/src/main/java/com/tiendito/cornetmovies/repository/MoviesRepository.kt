package com.tiendito.cornetmovies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tiendito.bmisrmovies.api.*
import com.tiendito.cornetmovies.api.MoviesApis
import com.tiendito.cornetmovies.db.RecentSearchDao
import com.tiendito.cornetmovies.db.RecentSearch
import com.tiendito.cornetmovies.model.Resource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApis: MoviesApis,
    private val recentSearchDao: RecentSearchDao
) {

    fun loadDiscoverMovies(): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            try {
                val result = moviesApis.getDiscoverMovies()

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()?.movies))
                else
                    emit(Resource.error(result.message(), null))

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }

        }
    }

    fun searchMovies(searchQuery: String, pageNumber: Int): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            try {
                val result = moviesApis.searchMovies(
                    searchQuery = searchQuery,
                    pageNumber = pageNumber
                )

                emit(Resource.complete(null))

                if (result.isSuccessful) {
                    emit(Resource.success(result.body()?.movies))
                    recentSearchDao.insertRecentSearch(RecentSearch(searchQuery))
                } else {
                    emit(Resource.error(result.message(), null))
                }

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }

        }
    }

    fun loadRecentSearch() : LiveData<List<RecentSearch>> {
        return recentSearchDao.loadRecentSearches()
    }
}