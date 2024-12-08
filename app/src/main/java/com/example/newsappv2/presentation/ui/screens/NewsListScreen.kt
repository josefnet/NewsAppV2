package com.example.newsappv2.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsappv2.R
import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.presentation.ui.components.NewsCard
import com.example.newsappv2.presentation.viewmodel.NewsViewModel
import com.example.newsappv2.util.Resource
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsListScreen(
    viewModel: NewsViewModel = koinViewModel(),
    modifier: Modifier
) {
    val newsState by viewModel.newsState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (newsState) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }

            is Resource.Success -> {

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(newsState.data ?: emptyList()) { article ->
                        NewsCard(article = article)
                    }
                }

            }

            is Resource.Error -> {
                Text(
                    text = newsState.message ?: stringResource(R.string.an_error_occurred_while_loading_news),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListScreenPreview() {
    val sampleArticles = listOf(
        Article(
            sourceName = "CNN",
            author = "John Doe",
            title = "Breaking News: Exciting Event",
            description = "This is a sample news description for testing.",
            url = "https://example.com",
            imageUrl = "https://via.placeholder.com/150",
            publishedAt = "2024-12-08",
            content = "Full content of the article."
        ),
        Article(
            sourceName = "BBC",
            author = "Jane Smith",
            title = "Tech Innovations of 2024",
            description = "An overview of the latest tech innovations in 2024.",
            url = "https://example.com",
            imageUrl = "https://via.placeholder.com/150",
            publishedAt = "2024-12-08",
            content = "Full content of the article."
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(sampleArticles) { article ->
                NewsCard(article = article)
            }
        }
    }
}