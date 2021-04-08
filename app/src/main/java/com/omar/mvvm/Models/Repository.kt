package com.omar.mvvm.Models

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.omar.mvvm.API.Interface.Endpoints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val unsplashApi: Endpoints) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
            pagingSourceFactory = { PagingSource(unsplashApi, query) }).liveData
}