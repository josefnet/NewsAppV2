package com.example.newsappv2.domain.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {
    private var inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private var outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    /**
     * Reformats a date string from the input format to the output format.
     *
     * @param dateString The original date string (e.g., "2024-12-08T16:19:00Z").
     * @return A formatted date string (e.g., "08 Dec 2024, 16:19"), or the original string if parsing fails.
     */
    fun reformat(dateString: String): String {
        return try {
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }
}