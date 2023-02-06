package com.example.pokemonapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideUtils {

    companion object {
        fun loadImage(imageView: ImageView, url: String?) {
            Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions().override(300, 300))
                .error(androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
                .into(imageView)
        }
    }

}