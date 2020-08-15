package `in`.androidplay.trackme.di

import `in`.androidplay.trackme.room.RunDatabase
import `in`.androidplay.trackme.util.Constants.KEY_FIRST_TIME_TOGGLE
import `in`.androidplay.trackme.util.Constants.KEY_NAME
import `in`.androidplay.trackme.util.Constants.KEY_WEIGHT
import `in`.androidplay.trackme.util.Constants.RUNNING_DATABASE_NAME
import `in`.androidplay.trackme.util.Constants.SHARED_PREFERENCE_NAME
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 7:46 AM
 */

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            RunDatabase::class.java,
            RUNNING_DATABASE_NAME
        ).build()


    @Singleton
    @Provides
    fun provideRunDao(db: RunDatabase) = db.getRunDao()


    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)


    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(KEY_WEIGHT, 80f)

    @Singleton
    @Provides
    fun provideFirsTimeToggle(sharedPreferences: SharedPreferences) =
        sharedPreferences.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}