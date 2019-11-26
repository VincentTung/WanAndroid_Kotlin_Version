package com.example.myapplication.adapter

/**
 * Created by VincentTung on 2019/11/26.
 */
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("cover")
fun bindCover(view: ImageView,url: String) {
    Glide.with(view.context).load(url).into(view)
}
