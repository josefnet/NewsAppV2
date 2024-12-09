package com.example.newsappv2.domain.usecase

import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.repository.NewsRepository
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.flow.Flow

class GetTopHeadlinesUseCase(private val repository: NewsRepository) {
    operator fun invoke(countryCode : String): Flow<Resource<List<Article>>> {
        return repository.getTopHeadlines(countryCode)
    }
}