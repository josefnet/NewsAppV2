package com.example.newsappv2.domain.repository

import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(country: String): Flow<Resource<List<Article>>>
}