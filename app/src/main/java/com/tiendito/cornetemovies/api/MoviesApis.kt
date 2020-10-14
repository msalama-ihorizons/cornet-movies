package com.tiendito.cornetemovies.api

import com.tiendito.bmisrmovies.api.*
import com.tiendito.cornetemovies.BuildConfig
import retrofit2.Response
import retrofit2.http.*

interface MoviesApis {

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Header("Authorization") token: String = BuildConfig.TOKEN,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Header("Authorization") token: String = BuildConfig.TOKEN,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") pageNumber: Int = 1,
        @Query("query") searchQuery: String
    ): Response<MoviesResponse>

}