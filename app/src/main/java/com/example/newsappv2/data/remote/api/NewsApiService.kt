package com.example.newsappv2.data.remote.api

import com.example.newsappv2.data.remote.dto.NewsResponseDto
import com.example.newsappv2.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponseDto
}