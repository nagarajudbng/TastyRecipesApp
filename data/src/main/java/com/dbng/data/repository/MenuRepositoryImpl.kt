package com.dbng.data.repository

import com.dbng.core.domain.Resource
import com.dbng.core.domain.utils.ResponseError
import com.dbng.data.datasource.MenuRemoteDataSource
import com.dbng.data.mapper.toDomain
import com.dbng.data.model.menuresponse.MenuItem
import com.dbng.domain.repository.MenuRepository
import retrofit2.HttpException
import java.io.IOException

class MenuRepositoryImpl(
    private val menuRemoteDataSource: MenuRemoteDataSource
) : MenuRepository {
    private var menuItemsCount = 0
    private val allMenuItems = mutableListOf<com.dbng.domain.model.MenuItem>()

    override suspend fun fetchMenuItems(from: Int, size: Int): Resource<List<com.dbng.domain.model.MenuItem>> {
        return try{
            val response = menuRemoteDataSource.fetchMenuItems(from,size)
            if(response.isSuccessful){
                menuItemsCount = response.body()?.count?:0
                val newItems = response.body()?.results?.map { it.toDomain() } ?: emptyList()
                allMenuItems.addAll(newItems)
                Resource.Success(data = newItems.toList())
            }
            else {
                Resource.Error(data = null,responseError = ResponseError.UnknownError)
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

    override suspend fun fetchMenuItemsMoreInfo(itemID: Int): Resource<com.dbng.domain.model.MenuItem> {
        return try {
            val response = menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)
            if (response.isSuccessful) {
                Resource.Success(data = response.body()?.toDomain())
            } else {
                Resource.Error(data = null, responseError = ResponseError.UnknownError)
            }
        } catch (e: IOException) {
            Resource.Error(data = null, responseError = ResponseError.NetworkError)
        } catch (e: HttpException) {
            Resource.Error(data = null, responseError = ResponseError.ServerError)
        }
    }
}
