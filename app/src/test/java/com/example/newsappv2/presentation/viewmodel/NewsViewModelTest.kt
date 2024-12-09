package com.example.newsappv2.presentation.viewmodel

import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsappv2.domain.util.CountryCodeProvider
import com.example.newsappv2.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // Mock the static method
        mockkStatic(CountryCodeProvider::class)
        every { CountryCodeProvider.getDefaultCountryCode() } returns "us"

        // Mock the use case
        getTopHeadlinesUseCase = mockk()

        // Initialize the ViewModel
        viewModel = NewsViewModel(getTopHeadlinesUseCase)
    }

    @Test
    fun `newsState should emit loading and success when use case returns data`() = runTest {
        // Arrange
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

        every { getTopHeadlinesUseCase("us") } returns flowOf(Resource.Success(articles))

        // Act
        val results = mutableListOf<Resource<List<Article>>>()
        viewModel.newsState.toList(results)

        // Assert
        assertEquals(Resource.Loading<List<Article>>(), results[0]) // Loading state
        assertEquals(Resource.Success(articles), results[1]) // Success state
    }

    @Test
    fun `newsState should emit loading and error when use case returns error`() = runTest {
        // Arrange
        val errorMessage = "Network error"

        every { getTopHeadlinesUseCase("us") } returns flowOf(Resource.Error(errorMessage))

        // Act
        val results = mutableListOf<Resource<List<Article>>>()
        viewModel.newsState.toList(results)

        // Assert
        assertEquals(Resource.Loading<List<Article>>(), results[0]) // Loading state
        assertEquals(Resource.Error<List<Article>>(errorMessage), results[1]) // Error state
    }
}
