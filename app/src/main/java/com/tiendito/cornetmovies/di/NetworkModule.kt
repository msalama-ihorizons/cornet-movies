package com.tiendito.cornetmovies.di



import com.tiendito.cornetmovies.api.MoviesApis
import com.tiendito.cornetmovies.utils.Constants.API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoviesService(): MoviesApis {

        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApis::class.java)
    }

}