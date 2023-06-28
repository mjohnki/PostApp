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
import de.johnki.data.post.PostDao
import de.johnki.data.post.PostEndpoint
import de.johnki.data.post.PostRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Singleton
    @Provides
    fun providesUserRepository(
        dao: UserDao
    ): UserRepository =
        UserRepository(dao)

    @Singleton
    @Provides
    fun providesPostRepository(
        dao: PostDao,
        endpoint: PostEndpoint
    ): PostRepository =
        PostRepository(dao, endpoint)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext baseApplication: Context) : AppDatabase =
        Room.databaseBuilder(
            baseApplication,
            AppDatabase::class.java, "app-db"
        ).build()

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao =
        database.userDao()

    @Singleton
    @Provides
    fun providePostDao(database: AppDatabase): PostDao =
        database.postDao()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
    }

    @Singleton
    @Provides
    fun providePhotoEndpoint(retrofit: Retrofit): PostEndpoint =
        retrofit.create(PostEndpoint::class.java)
}
