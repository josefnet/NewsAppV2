package com.example.newsappv2.data.repository

import com.example.newsappv2.data.remote.api.NewsApiService
import com.example.newsappv2.data.remote.dto.ArticleDto
import com.example.newsappv2.data.remote.dto.NewsResponseDto
import com.example.newsappv2.data.remote.dto.SourceDto
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class NewsRepositoryImplTest {

    @Mock
    private lateinit var apiService: NewsApiService

    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = NewsRepositoryImpl(apiService)
    }

    @Test
    fun `getTopHeadlines emits loading and success when API call is successful`() = runTest {
        // Given
        val country = "us"
        val articlesDto = listOf(
            ArticleDto(
                source = SourceDto(id = "cnn", name = "CNN"),
                author = "John Doe",
                title = "Breaking News",
                description = "Description",
                url = "https://example.com",
                urlToImage = "https://via.placeholder.com/150",
                publishedAt = "2024-12-08",
                content = "Full content"
            )
        )
        val response = NewsResponseDto(
            status = "ok",
            totalResults = 1,
            articles = articlesDto
        )

        // When
        Mockito.`when`(apiService.getTopHeadlines(country)).thenReturn(response)

        // Then
        val result = repository.getTopHeadlines(country).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
    }

    @Test
    fun `getTopHeadlines emits loading and error when API call fails`() = runTest {
        // Given
        val country = "us"
        val errorMessage = "Network error"

        // When
        Mockito.`when`(apiService.getTopHeadlines(country)).thenThrow(RuntimeException(errorMessage))

        // Then
        val result = repository.getTopHeadlines(country).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
    }
}
