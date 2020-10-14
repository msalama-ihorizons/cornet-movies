package com.tiendito.cornetemovies.di

import android.content.Context
import androidx.room.Room
import com.tiendito.cornetemovies.db.RecentSearchDao
import com.tiendito.cornetemovies.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Mohamed Salama on 10/13/2020.
 */

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
        return  return Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "movies.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideKeyWordsDao(db: MoviesDatabase): RecentSearchDao {
        return db.recentSearchDao()
    }

}