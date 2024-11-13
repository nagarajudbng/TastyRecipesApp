package com.dbng.data.di

import com.dbng.data.repository.MenuRepositoryImpl
import com.dbng.domain.repository.MenuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  MenuModuleAbstract{

    @Binds
    @Singleton
    abstract fun bindMenuRepository(
        menuRepositoryImpl: MenuRepositoryImpl
    ): MenuRepository
}
