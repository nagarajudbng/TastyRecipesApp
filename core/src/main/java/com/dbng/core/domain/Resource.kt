package com.dbng.core.domain

import com.dbng.core.domain.utils.ResponseError

sealed class Resource<T>(val data:T?=null,val responseError: ResponseError?=null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(data: T?,responseError: ResponseError?=null): Resource<T>(data,responseError)
}