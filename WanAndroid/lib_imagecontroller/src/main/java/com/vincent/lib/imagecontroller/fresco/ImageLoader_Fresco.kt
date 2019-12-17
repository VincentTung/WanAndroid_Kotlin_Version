package com.vincent.lib.imagecontroller.fresco

import android.content.Context
import android.widget.ImageView
import com.facebook.drawee.backends.pipeline.Fresco
import com.vicnent.lib.base.util.ContextUtil
import com.vincent.lib.imagecontroller.ImageDisplayParams
import com.vincent.lib.imagecontroller.ImageLoader

class ImageLoader_Fresco :ImageLoader{
    override fun init() {
       Fresco.initialize(ContextUtil.context)
    }

    override fun load(imageDisplayParams: ImageDisplayParams) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(img: ImageView, imageUrl: String) {
//       Fresco.
    }

    override fun load(img: ImageView, imageUrl: String, roundValue: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(img: ImageView, imgResourceId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(img: ImageView, imgResourceId: Int, roundValue: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNetBitmap(
        context: Context,
        imageUrl: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocalBitmap(
        context: Context,
        path: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun download(
        context: Context,
        imgUrl: String,
        listener: ImageLoader.DownloadImageListener
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearMemory(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearImageCache(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}