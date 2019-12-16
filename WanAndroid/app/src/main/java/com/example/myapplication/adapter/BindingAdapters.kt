package com.example.myapplication.adapter

/**
 *
 */
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("cover")
fun bindCover(view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}
