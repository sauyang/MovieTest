package com.example.myapplication.module

import com.example.myapplication.repository.API
import com.example.myapplication.repository.ApiKeyInterceptor
import com.example.myapplication.repository.MovieRepoImpl
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = Constants.API_KEY

    @Provides
    @Singleton
    @IntoSet
    fun provideApiKeyInterceptor(@Named("apiKey") apiKey: String): Interceptor {
        return ApiKeyInterceptor(apiKey)
    }


    @Singleton
    @Provides
    fun provideHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .apply {
                interceptors().addAll(interceptors)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient, gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): API {
        return retrofitBuilder
            .build()
            .create(API::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        api: API
    ) = MovieRepoImpl(api) as MovieRepository

}