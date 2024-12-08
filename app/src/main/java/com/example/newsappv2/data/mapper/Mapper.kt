package com.example.newsappv2.data.mapper

import com.example.newsappv2.data.remote.dto.ArticleDto
import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.util.DateFormatter


fun ArticleDto.toDomain(): Article {
    return Article(
        sourceName = this.source?.name.orEmpty(),
        author = this.author.orEmpty(),
        title = this.title.orEmpty(),
        description = this.description.orEmpty(),
        url = this.url.orEmpty(),
        imageUrl = this.urlToImage.orEmpty(),
        publishedAt = this.publishedAt?.let { DateFormatter.reformat(it) }.orEmpty(),
        content = this.content.orEmpty()
    )
}