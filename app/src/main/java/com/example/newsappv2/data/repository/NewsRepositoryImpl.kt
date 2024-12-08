package com.example.newsappv2.data.repository


import com.example.newsappv2.data.mapper.toDomain
import com.example.newsappv2.data.remote.api.NewsApiService
import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.repository.NewsRepository
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    private val apiService: NewsApiService
) : NewsRepository {
    override fun getTopHeadlines(country: String): Flow<Resource<List<Article>>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getTopHeadlines(country)
            val articles = response.articles.map { it.toDomain() }
            emit(Resource.Success(articles))
        } catch (e: Exception) {
            emit(Resource.Error("Error fetching news: ${e.localizedMessage}"))
        }
    }
}