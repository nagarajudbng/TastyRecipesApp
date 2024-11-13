package com.dbng.core.di

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.dbng.core.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "")
                val modifiedRequest = it.request().newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
                    .addHeader("x-rapidapi-key", "b1f76604e2msh922e1e2bf913e31p1fba1ejsneb8f064b5c2d")
                    .addHeader("x-rapidapi-host", "tasty.p.rapidapi.com")
                    .build()
                it.proceed(modifiedRequest)
            }
            .addInterceptor(Interceptor { chain ->
                val originalRequest: Request = chain.request()
                Log.d("Host",originalRequest.url.host)
                val newRequest: Request = originalRequest.newBuilder()
                    .header("Host", originalRequest.url.host)
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept", "*/*")
                    .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences {
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext app:Context): ContentResolver {
        return app.contentResolver
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

//    @Provides
//    @Singleton
//    fun provideImageLoader(context: Context, sharedPreferences: SharedPreferences): ImageLoader {
//        val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "")
//        val client = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val newRequest: Request = chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .build()
//                chain.proceed(newRequest)
//            }
//            .build()
//
//        return ImageLoader.Builder(context)
//            .okHttpClient(client)
//            .crossfade(true)
//            .diskCachePolicy(CachePolicy.ENABLED) // Change as needed
//            .build()
//    }
}