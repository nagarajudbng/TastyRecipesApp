package com.dbng.di

import com.dbng.data.datasource.MenuRemoteDataSource
import com.dbng.data.network.MenuApiService
import com.dbng.data.repository.MenuRepositoryImpl
import com.dbng.domain.repository.MenuRepository
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
    fun provideMenuRepository(menuRemoteDataSource: MenuRemoteDataSource): MenuRepositoryImpl{
        return MenuRepositoryImpl(menuRemoteDataSource)
    }
}
