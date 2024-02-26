package ru.alexadler9.canvas.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexadler9.canvas.data.local.AppPreferencesSource
import ru.alexadler9.canvas.data.local.IAppPreferencesSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefsModule {

    @Singleton
    @Provides
    fun provideAppPreferencesSource(@ApplicationContext appContext: Context): IAppPreferencesSource {
        return AppPreferencesSource(appContext)
    }
}