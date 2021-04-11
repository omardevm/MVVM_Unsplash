package com.omar.mvvm.Views.Fragments.GalleryDetail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.omar.mvvm.R
import com.omar.mvvm.databinding.FragmentGalleryDetailBinding

class GalleryDetailFragment : Fragment(R.layout.fragment_gallery_detail) {
    private val args by navArgs<GalleryDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentGalleryDetailBinding.bind(view)
        binding.apply {
            val photo = args.photo
            Glide.with(this@GalleryDetailFragment)
                .load(photo.urls.full)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressDetail.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressDetail.isVisible = false
                        txtCreator.isVisible = true
                        txtDetail.isVisible = photo.description != null
                        return false
                    }

                })
                .error(R.drawable.ic_launcher_foreground)
                .into(imgDetail)
            txtDetail.text = photo.description
            val uri = Uri.parse(photo.user.attrUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            txtCreator.apply {
                val value = "Photo by ${photo.user.name} on Unsplash"
                text = value
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true //link effect
            }
        }
    }

}