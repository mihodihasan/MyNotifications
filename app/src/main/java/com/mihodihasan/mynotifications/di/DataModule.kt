package com.mihodihasan.mynotifications.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mihodihasan.mynotifications.data.db.PermanentDatabase
import com.mihodihasan.mynotifications.domain.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Singleton
    @Provides
    fun providesSharedPreferencesEditor(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun providesPermanentDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        PermanentDatabase::class.java, Constants.ROOM_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun providesNotificationDao(permanentDatabase: PermanentDatabase) =
        permanentDatabase.getNotificationDao()


}
