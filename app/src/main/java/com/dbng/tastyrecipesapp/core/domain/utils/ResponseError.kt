package com.dbng.tastyrecipesapp.core.domain.utils


// Created by Nagaraju on 11/11/24.

sealed class ResponseError {
    data object NetworkError : ResponseError()
    data object ServerError : ResponseError()
    data object UnknownError : ResponseError()
    data object NoDataFoundError : ResponseError()

}