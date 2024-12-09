package com.example.newsappv2.domain.usecase

import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.repository.NewsRepository
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class GetTopHeadlinesUseCaseTest {

    @Mock
    private lateinit var repository: NewsRepository

    private lateinit var useCase: GetTopHeadlinesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetTopHeadlinesUseCase(repository)
    }

    @Test
    fun `invoke should emit success when repository returns data`() = runTest {
        // Given
        val countryCode = "us"
        val articles = listOf(
            Article(
                sourceName = "CNN",
                author = "John Doe",
                title = "Breaking News",
                description = "Description",
                url = "https://example.com",
                imageUrl = "https://via.placeholder.com/150",
                publishedAt = "2024-12-08",
                content = "Full content"
            )
        )

        // When
        Mockito.`when`(repository.getTopHeadlines(countryCode))
            .thenReturn(flowOf(Resource.Success(articles)))

        // Then
        val result = useCase(countryCode).toList()

        assertEquals(1, result.size) // Only one emission (Success)
        assertTrue(result[0] is Resource.Success)
        assertEquals(articles, (result[0] as Resource.Success).data)
        Mockito.verify(repository).getTopHeadlines(countryCode)

    }

    @Test
    fun `invoke should emit error when repository throws an exception`() = runTest {
        // Given
        val countryCode = "us"
        val errorMessage = "Network error"

        // When
        Mockito.`when`(repository.getTopHeadlines(countryCode))
            .thenReturn(flowOf(Resource.Error(errorMessage)))

        // Then
        val result = useCase(countryCode).toList()

        assertEquals(1, result.size) // Only one emission (Error)
        assertTrue(result[0] is Resource.Error)
        assertEquals(errorMessage, (result[0] as Resource.Error).message)
        Mockito.verify(repository).getTopHeadlines(countryCode)
    }

    @Test
    fun `invoke should emit loading followed by success`() = runTest {
        // Given
        val countryCode = "us"
        val articles = listOf(
            Article(
                sourceName = "CNN",
                author = "John Doe",
                title = "Breaking News",
                description = "Description",
                url = "https://example.com",
                imageUrl = "https://via.placeholder.com/150",
                publishedAt = "2024-12-08",
                content = "Full content"
            )
        )

        // When
        Mockito.`when`(repository.getTopHeadlines(countryCode))
            .thenReturn(flowOf(Resource.Loading(), Resource.Success(articles)))

        // Then
        val result = useCase(countryCode).toList()

        assertEquals(2, result.size) // Loading and Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(articles, (result[1] as Resource.Success).data)
        Mockito.verify(repository).getTopHeadlines(countryCode)
    }
}
