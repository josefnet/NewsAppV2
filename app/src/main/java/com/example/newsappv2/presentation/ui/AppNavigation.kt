package com.example.newsappv2.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsappv2.presentation.ui.screens.DetailArticleScreen
import com.example.newsappv2.presentation.ui.screens.NewsListScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = "newsList",  modifier = modifier) {
        composable("newsList") {
            NewsListScreen(navController = navController, modifier = modifier)
        }
        composable("detailArticle/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            DetailArticleScreen(navController = navController, index = index)
        }
    }
}