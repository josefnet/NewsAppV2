package com.example.newsappv2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappv2.domain.model.Article
import com.example.newsappv2.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsappv2.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    private val _newsState = MutableStateFlow<Resource<List<Article>>>(Resource.Loading())
    val newsState: StateFlow<Resource<List<Article>>> = _newsState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            _newsState.value = Resource.Loading()
            getTopHeadlinesUseCase().collect { result ->
                _newsState.value = Resource.Success(result.data ?: emptyList())
            }
        }
    }
}