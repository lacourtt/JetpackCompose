package com.lacourt.githubusers.di

import com.lacourt.githubusers.AppConstants
import com.lacourt.githubusers.repository.Repository
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.calladapter.NetworkResponseAdapterFactory
import okhttp3.Interceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { providesInterceptor() }
    factory { providesRetrofitInstance() }
    single { service(get()) }
    factory { providesRepository(get()) }
}

fun providesRepository(service: GithubApiService): Repository {
    return Repository(service)
}

fun providesInterceptor(): Interceptor {
    return Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
//            .addHeader("Authorization", "Bearer ${AppConstants.TOKEN}") // Insira sua chave do Github API, se necessário
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }
}

fun providesRetrofitInstance(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .build()
}

fun service(retrofit: Retrofit): GithubApiService {
    return retrofit.create(GithubApiService::class.java)
}