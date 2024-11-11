package com.dbng.tastyrecipesapp.feature_menu.di

import com.dbng.tastyrecipesapp.feature_menu.data.datasource.MenuRemoteDataSource
import com.dbng.tastyrecipesapp.feature_menu.data.network.MenuApiService
import com.dbng.tastyrecipesapp.feature_menu.data.repository.MenuRepositoryImpl
import com.dbng.tastyrecipesapp.feature_menu.domain.repository.MenuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MenuModule{
    @Provides
    @Singleton
    fun provideProductsAPI(retrofit: Retrofit): MenuApiService {
        return retrofit.create(MenuApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
    @Provides
    @Singleton
    fun provideMenuRemoteDataSource(menuAPI: MenuApiService,ioDispatcher: CoroutineDispatcher): MenuRemoteDataSource {
        return MenuRemoteDataSource(menuAPI,ioDispatcher)
    }


    @Provides
    @Singleton
    fun provideMenuRepository(menuRemoteDataSource: MenuRemoteDataSource): MenuRepository {
        return MenuRepositoryImpl(menuRemoteDataSource)
    }
}
