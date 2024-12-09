package com.example.newsappv2.domain.usecase

import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.repository.NewsRepository
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetTopHeadlinesUseCaseTest {

    @Mock
    private lateinit var repository: NewsRepository

    private lateinit var useCase: GetTopHeadlinesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this) // Initialize Mockito
        useCase = GetTopHeadlinesUseCase(repository)
    }

    @Test
    fun `invoke should return articles from repository with correct country code`() = runBlockingTest {
        // Arrange
        val countryCode = "us"
        val articles = listOf(
            Article(
                sourceName = "Example Source",
                author = "Author Name",
                title = "Sample Title",
                description = "Sample Description",
                url = "https://example.com",
                imageUrl = "https://via.placeholder.com/150",
                publishedAt = "2024-12-08",
                content = "Sample Content"
            )
        )

        Mockito.`when`(repository.getTopHeadlines(countryCode)).thenReturn(flowOf(Resource.Success(articles)))

        // Act
        val result = useCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(Resource.Success(articles), result.first())

        // Verify interactions
        Mockito.verify(repository).getTopHeadlines(countryCode)
    }

    @Test
    fun `invoke should handle error from repository`() = runBlockingTest {
        // Arrange
        val countryCode = "us"
        val errorMessage = "Network error"

        Mockito.`when`(repository.getTopHeadlines(countryCode)).thenReturn(flowOf(Resource.Error(errorMessage)))

        // Act
        val result = useCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(Resource.Error<List<Article>>(errorMessage), result.first())

        // Verify interactions
        Mockito.verify(repository).getTopHeadlines(countryCode)
    }
}