package com.vincent.lib.imagecontroller

import android.content.Context
import android.widget.ImageView
import com.vincent.lib.imagecontroller.glide.ImageLoader_Glide

class ImageController private constructor() : ImageLoader {

    private var mImageLoader: ImageLoader? = null

    private object InstanceHolder {
        val OUR_INSTANCE = ImageController()
    }

    fun setGlideLoaderAndInit() {
        this.mImageLoader = ImageLoader_Glide()
        init()
    }

    fun setLoader(loader: ImageLoader): ImageController {
        this.mImageLoader = loader
        return this
    }

    override fun init() {
        mImageLoader?.init()
    }

    override fun load(imageDisplayParams: ImageDisplayParams) {
        mImageLoader?.load(imageDisplayParams)
    }

    override fun load(img: ImageView, imageUrl: String) {
        mImageLoader?.load(img, imageUrl)
    }

    override fun load(img: ImageView, imageUrl: String, roundValue: Int) {
        mImageLoader?.load(img, imageUrl, roundValue)
    }

    override fun load(img: ImageView, imgResourceId: Int) {
        mImageLoader?.load(img, imgResourceId)
    }

    override fun load(img: ImageView, imgResourceId: Int, roundValue: Int) {
        mImageLoader?.load(img, imgResourceId, roundValue)
    }

    override fun getNetBitmap(
        context: Context,
        imageUrl: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        mImageLoader?.getNetBitmap(context, imageUrl, listener)
    }

    override fun getLocalBitmap(
        context: Context,
        path: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        mImageLoader?.getLocalBitmap(context, path, listener)
    }

    override fun download(
        context: Context,
        imgUrl: String,
        listener: ImageLoader.DownloadImageListener
    ) {
        mImageLoader?.download(context, imgUrl, listener)
    }

    override fun clearMemory(context: Context) {
        mImageLoader?.clearMemory(context)
    }

    override fun clearImageCache(context: Context) {
        mImageLoader?.clearMemory(context)
    }

    companion object {
        const val TAG = "ImageController"
        const val NULL_LOADER_TIP =
            "ImageLoader can't be null, call method setLoader() to set a loader"


        fun getInstance(): ImageController {
            return InstanceHolder.OUR_INSTANCE
        }
    }
}