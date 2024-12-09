package com.example.newsappv2.presentation.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.R

@Composable
fun NewsCard(article: Article,onClick: () -> Unit) {
    val context: Context = LocalContext.current
    val cardShape = RoundedCornerShape(5)
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(cardShape)
            .border(1.dp, Color.Gray, cardShape)
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.Top
        ) {

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
                    .size(100.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.title,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.author,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.publishedAt,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
            }
        }

        Text(
            text = article.description,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    NewsCard(
        article = Article(
            sourceName = "CNN",
            author = "Sana Noor Haq, Catherine Nicholls, Tori B. Powell",
            title = "Syria’s rebels say they are encircling Damascus but army denies retreat: Live updates - CNN",
            description = "Syria’s rebel alliance is edging closer to the capital Damascus, after anti-regime forces swept across the country in a lightning offensive. Follow the latest live news updates here.",
            url = "https://www.cnn.com/world/live-news/syria-civil-war-12-07-2024-intl/index.html",
            imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/gettyimages-2186912814.jpg?c=16x9&q=w_800,c_fill",
            publishedAt = "2024-12-08",
            content = "The renewed fighting in Syrias civil war, which has killed more than 300,000 people and sent nearly 6 million refugees out of the country since 2011, will have wide ramifications across the Middle Ea…"
        )
    ) {}
}