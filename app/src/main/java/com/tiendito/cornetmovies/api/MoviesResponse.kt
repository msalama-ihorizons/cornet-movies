package com.tiendito.bmisrmovies.api

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MoviesResponse(@SerializedName("results") val movies: List<Movie>)

data class Movie(
    @PrimaryKey val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("overview") val overview: String
)