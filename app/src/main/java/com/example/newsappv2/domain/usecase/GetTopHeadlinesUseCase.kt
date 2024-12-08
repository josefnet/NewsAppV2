package com.example.newsappv2.domain.usecase

import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.repository.NewsRepository
import com.example.newsappv2.domain.util.CountryCodeProvider
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.flow.Flow

class GetTopHeadlinesUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<Resource<List<Article>>> {
        val countryCode = CountryCodeProvider.getDefaultCountryCode()
        return repository.getTopHeadlines(countryCode)
    }
}