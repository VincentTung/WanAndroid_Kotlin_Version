package com.vincent.lib.imagecontroller.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.vincent.lib.imagecontroller.ImageDisplayParams
import com.vincent.lib.imagecontroller.ImageLoader
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.transition.Transition
import java.io.File


class ImageLoader_Glide : ImageLoader {
    override fun init() {
    }

    override fun load(params: ImageDisplayParams) {
        params.imageUrl?.let { url ->
            params.imageView?.let { imgView ->
                load(
                    url,
                    imgView,
                    params.width,
                    params.height,
                    params.errorResourceId,
                    params.placeHolderResourceId,
                    params.roundValue
                )
            }
        }
    }

    override fun load(img: ImageView, imageUrl: String) {
        Glide.with(img.context).load(imageUrl).into(img)
    }

    override fun load(img: ImageView, imageUrl: String, roundValue: Int) {
        Glide.with(img.context).load(imageUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundValue)))
            .into(img)

    }

    private fun load(
        imgUrl: String,
        img: ImageView,
        width: Int,
        height: Int,
        errorResourceId: Int,
        placeHolderResourceId: Int,
        roundValue: Int
    ) {

        val builder = Glide.with(img.context).load(imgUrl)
        val options = RequestOptions()
        if (width > 0 && height > 0) {
            options.override(width, height)
        }
        if (errorResourceId > 0) {
            options.error(errorResourceId)
        }
        if (placeHolderResourceId > 0) {
            options.placeholder(placeHolderResourceId)
        }
        if (roundValue > 0) {
            options.transform(RoundedCorners(roundValue))
        }
        builder.apply(options).into(img)

    }

    override fun load(img: ImageView, imgResourceId: Int) {
        Glide.with(img.context).load(imgResourceId).into(img)
    }

    override fun load(img: ImageView, imgResourceId: Int, roundValue: Int) {
        Glide.with(img.context).load(imgResourceId)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundValue)))
            .into(img)
    }

    override fun getNetBitmap(
        context: Context,
        imageUrl: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        Glide.with(context).asBitmap().load(imageUrl).into(object : Target<Bitmap> {
            override fun onLoadStarted(placeholder: Drawable?) {
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                listener.onGetBitmapFinish(null)
            }

            override fun getSize(cb: SizeReadyCallback) {

            }

            override fun getRequest(): Request? {
                return null
            }

            override fun onStop() {
            }

            override fun setRequest(request: Request?) {
            }

            override fun removeCallback(cb: SizeReadyCallback) {
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                listener.onGetBitmapFinish(resource)
            }

            override fun onStart() {
            }

            override fun onDestroy() {
            }
        })
    }

    override fun getLocalBitmap(
        context: Context,
        path: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        getNetBitmap(context, path, listener)
    }

    override fun download(
        context: Context,
        imgUrl: String,
        listener: ImageLoader.DownloadImageListener
    ) {
        val requestManager = Glide.with(context)
        requestManager.load(imgUrl)
        requestManager.downloadOnly().into(object : Target<File> {
            override fun onLoadStarted(placeholder: Drawable?) {
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                listener.onDownloadImageFinish(null)
            }

            override fun getSize(cb: SizeReadyCallback) {
            }

            override fun getRequest(): Request? {
                return null
            }

            override fun onStop() {
            }

            override fun setRequest(request: Request?) {
            }

            override fun removeCallback(cb: SizeReadyCallback) {
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                listener.onDownloadImageFinish(resource.absolutePath)
            }

            override fun onStart() {
            }

            override fun onDestroy() {
            }

        })
    }

    override fun clearMemory(context: Context) {
        Glide.get(context).clearMemory()
    }

    override fun clearImageCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }
}