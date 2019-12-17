package com.vincent.funny.kotlin.adapter

/**
 *
 */
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.vincent.lib.imagecontroller.ImageController

@BindingAdapter("cover")
fun bindCover(view: ImageView, url: String) {
    ImageController.getInstance().load(view,url,18)
}
