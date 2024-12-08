package com.example.newsappv2.domain.util

import java.util.Locale

object CountryCodeProvider {
    /**
     * Gets the ISO country code of the user's current locale.
     * Defaults to "us" if the locale is invalid.
     *
     * @return The ISO country code (e.g., "us", "fr").
     */
    fun getDefaultCountryCode(): String {
        return try {
            Locale.getDefault().country.lowercase(Locale.getDefault())
        } catch (e: Exception) {
            "us" // Fallback to "us" if locale is unavailable or invalid
        }
    }
}