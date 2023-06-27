package de.johnki.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.johnki.data.AppDatabase
import de.johnki.data.login.UserDao
import de.johnki.data.login.UserRepository

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    fun providesUserRepository(
        dao: UserDao
    ): UserRepository =
        UserRepository(dao)

    @Provides
    fun provideDataBase(@ApplicationContext baseApplication: Context) : AppDatabase =
        Room.databaseBuilder(
            baseApplication,
            AppDatabase::class.java, "app-db"
        ).build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao =
        database.userDao()
}
