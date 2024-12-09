package com.example.newsappv2.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.newsappv2.R
import com.example.newsappv2.presentation.viewmodel.NewsViewModel
import com.example.newsappv2.util.Resource
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailArticleScreen(navController: NavController, index: Int) {
    val viewModel: NewsViewModel = koinViewModel()
    val context = LocalContext.current
    val newsState by viewModel.newsState.collectAsState()

    // Extract the article from the state
    val article = (newsState as? Resource.Success)?.data?.getOrNull(index)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        article?.let {
            Text(text = article.title, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)

            Text(text = "By ${article.author}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(article.imageUrl)
                        .error(R.drawable.ic_launcher)
                        .placeholder(R.drawable.ic_launcher)
                        .build()
                ),
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f),
                contentScale = ContentScale.Crop
            )

            Text(text = article.description, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

            Text(text = article.content, style = androidx.compose.material3.MaterialTheme.typography.bodySmall)

            Text(text = "Published at: ${article.publishedAt}", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            }) {
                Text(text = "Read More")
            }

        }

    }
}