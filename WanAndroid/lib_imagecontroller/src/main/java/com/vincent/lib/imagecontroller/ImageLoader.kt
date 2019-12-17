package com.vincent.lib.imagecontroller

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView

interface ImageLoader {
    interface GetBitmapListener {
        fun onGetBitmapFinish(bitmap: Bitmap?)
    }

    interface DownloadImageListener {
        fun onDownloadImageFinish(downloadPath: String?)
    }

    fun init()
    fun load(imageDisplayParams: ImageDisplayParams)
    fun load(img: ImageView, imageUrl: String)
    fun load(img: ImageView, imageUrl: String, roundValue: Int)
    fun load(img: ImageView, imgResourceId: Int)
    fun load(img: ImageView, imgResourceId: Int, roundValue: Int)

    fun getNetBitmap(context: Context, imageUrl: String, listener: GetBitmapListener)
    fun getLocalBitmap(context: Context, path: String, listener: GetBitmapListener)

    fun download(context: Context, imgUrl: String, listener: DownloadImageListener)

    fun clearMemory(context: Context)
    fun clearImageCache(context: Context)

}