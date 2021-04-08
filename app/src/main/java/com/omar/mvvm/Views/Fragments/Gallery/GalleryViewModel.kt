package com.omar.mvvm.Views.Fragments.Gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omar.mvvm.Models.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val currencyQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currencyQuery.switchMap { qs ->
        repository.getSearchResults(qs).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currencyQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }
}