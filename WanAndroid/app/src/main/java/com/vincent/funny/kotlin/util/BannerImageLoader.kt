package com.vincent.funny.kotlin.util

import android.content.Context
import android.widget.ImageView
import com.vincent.lib.imagecontroller.ImageController
import com.youth.banner.loader.ImageLoader

class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (context != null) {
            if (imageView != null) {
                ImageController.getInstance().load(imageView,path as String)
            }
        }
    }
}