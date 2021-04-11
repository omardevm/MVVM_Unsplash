package com.omar.mvvm.Views.Fragments.Gallery

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.omar.mvvm.Models.Repository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: Repository,
    @Assisted state: SavedStateHandle
) : ViewModel() {
    private val currencyQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currencyQuery.switchMap { qs ->
        repository.getSearchResults(qs).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currencyQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_currency"
        private const val DEFAULT_QUERY = "cats"
    }
}