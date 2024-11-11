package com.dbng.tastyrecipesapp.core.domain

import com.dbng.tastyrecipesapp.core.domain.utils.ResponseError

sealed class Resource<T>(val data:T?=null,val responseError: ResponseError?=null) {
    class Success<T>(data: T?):Resource<T>(data)
    class Error<T>(data: T?,responseError: ResponseError?=null):Resource<T>(data,responseError)
}