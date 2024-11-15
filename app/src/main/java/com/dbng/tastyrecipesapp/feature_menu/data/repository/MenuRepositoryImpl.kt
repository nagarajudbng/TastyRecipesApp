package com.dbng.tastyrecipesapp.feature_menu.data.repository

import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.core.domain.utils.ResponseError
import com.dbng.tastyrecipesapp.feature_menu.data.datasource.MenuRemoteDataSource
import com.dbng.tastyrecipesapp.feature_menu.data.mapper.toDomain
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.domain.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MenuRepositoryImpl(
    private val menuRemoteDataSource: MenuRemoteDataSource
) : MenuRepository {
    private var menuItemsCount = 0
    private val allMenuItems = mutableListOf<com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem>()

    override suspend fun fetchMenuItems(from: Int, size: Int): Resource<List<com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem>> {
        return try{
            withContext(Dispatchers.IO) {
                val response = menuRemoteDataSource.fetchMenuItems(from, size)
                if (response.isSuccessful) {
                    menuItemsCount = response.body()?.count ?: 0
                    val newItems = response.body()?.results?.map { it.toDomain() } ?: emptyList()
                    allMenuItems.addAll(newItems)
                    Resource.Success(data = newItems.toList())
                } else {
                    Resource.Error(data = null, responseError = ResponseError.UnknownError)
                }
            }
        } catch(e: IOException) {
            Resource.Error(data = null,responseError = ResponseError.NetworkError)
        } catch(e: HttpException) {
            Resource.Error(data = null,responseError = ResponseError.ServerError)
        }
    }

    override fun getTotalItemCount(): Int {
        return menuItemsCount
    }

    override suspend fun fetchMenuItemsMoreInfo(itemID: Int): Resource<com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem> {
        return try {
            withContext(Dispatchers.IO) {
                val response = menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)
                if (response.isSuccessful) {
                    Resource.Success(data = response.body()?.toDomain())
                } else {
                    Resource.Error(data = null, responseError = ResponseError.UnknownError)
                }
            }
        } catch (e: IOException) {
            Resource.Error(data = null, responseError = ResponseError.NetworkError)
        } catch (e: HttpException) {
            Resource.Error(data = null, responseError = ResponseError.ServerError)
        }
    }
}
