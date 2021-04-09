package com.omar.mvvm.Views.Fragments.Gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.omar.mvvm.Models.Response.SearchPhotos.UnsplashPhoto
import com.omar.mvvm.R
import com.omar.mvvm.Utils.PagingPhotoAdapter
import com.omar.mvvm.Utils.UnsplashPhotoLoadStateAdapter
import com.omar.mvvm.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    PagingPhotoAdapter.OnItemClickListener {
    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        val adapter = PagingPhotoAdapter(this)
        binding.apply {
            unsplashRecycler.setHasFixedSize(true)
            unsplashRecycler.itemAnimator = null
            unsplashRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { ls ->
            binding.apply {
                progress.isVisible = ls.source.refresh is LoadState.Loading
                unsplashRecycler.isVisible = ls.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = ls.source.refresh is LoadState.Error
                txtError404.isVisible = ls.source.refresh is LoadState.Error
                if (ls.source.refresh is LoadState.NotLoading && ls.append.endOfPaginationReached && adapter.itemCount < 1) {
                    unsplashRecycler.isVisible = false
                    txtErrorEmpty.isVisible = true
                } else {
                    txtErrorEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.unsplashRecycler.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: UnsplashPhoto) {

    }
}