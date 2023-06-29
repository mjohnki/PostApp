package de.johnki.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {
    @Singleton
    @Provides
    fun providesAppNavigatorImpl() : AppNavigator = AppNavigatorImpl()
}
