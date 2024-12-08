package com.example.newsappv2.di

import com.example.newsappv2.data.remote.api.NewsApiService
import com.example.newsappv2.data.repository.NewsRepositoryImpl
import com.example.newsappv2.domain.repository.NewsRepository
import com.example.newsappv2.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsappv2.presentation.viewmodel.NewsViewModel
import com.example.newsappv2.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(NewsApiService::class.java)
    }

    factory<NewsRepository> { NewsRepositoryImpl(get()) }
    factory { GetTopHeadlinesUseCase(get()) }
    viewModel { NewsViewModel(get()) }
}