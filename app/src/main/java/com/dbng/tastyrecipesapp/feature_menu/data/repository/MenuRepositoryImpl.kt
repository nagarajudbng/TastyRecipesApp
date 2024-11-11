package com.dbng.tastyrecipesapp.feature_menu.data.repository

import com.dbng.tastyrecipesapp.core.domain.Resource
import com.dbng.tastyrecipesapp.core.domain.utils.ResponseError
import com.dbng.tastyrecipesapp.feature_menu.data.datasource.MenuRemoteDataSource
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuResponse
import com.dbng.tastyrecipesapp.feature_menu.data.model.menuresponse.MenuItem
import com.dbng.tastyrecipesapp.feature_menu.domain.repository.MenuRepository
import retrofit2.HttpException
import java.io.IOException

class MenuRepositoryImpl(
    private val menuRemoteDataSource: MenuRemoteDataSource
) : MenuRepository {
    var menuItemsCount = 0
    override suspend fun fetchMenuItems(from: Int, size: Int): Resource<List<com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem>> {
        return try{
            val response = menuRemoteDataSource.fetchMenuItems(from,size)
            var resource: Resource.Success<MenuResponse>? = null
            if(response.isSuccessful){
                menuItemsCount = response.body()?.count?:0
                resource = Resource.Success(data= response.body())
                Resource.Success(data= resource.data?.results?.map { it.toDomain() })
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

    override suspend fun fetchMenuItemMoreInfo(itemID:Int): Resource<com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem> {
        return try{
            val response = menuRemoteDataSource.fetchMenuItemMoreInfo(itemID)
            var resource: Resource.Success<MenuItem>? = null
            if(response.isSuccessful){
                resource = Resource.Success(data= response.body())
                Resource.Success(data= resource.data?.toDomain())
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

    fun MenuItem.toDomain(): com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem {
        return com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem(id?:0, name?:"a",thumbnailUrl?:"url")
    }

}
