package com.vincent.lib.imagecontroller.fresco

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.Priority
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest.RequestLevel
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.vicnent.lib.base.util.ContextUtil
import com.vincent.lib.imagecontroller.ImageDisplayParams
import com.vincent.lib.imagecontroller.ImageLoader
import com.vincent.lib.imagecontroller.util.BitmapUtil.Companion.getPath
import com.vincent.lib.imagecontroller.util.BitmapUtil.Companion.saveBitmap


class ImageLoader_Fresco : ImageLoader {
    override fun init() {
        Fresco.initialize(ContextUtil.getContext())
    }

    override fun load(imageDisplayParams: ImageDisplayParams) {

        imageDisplayParams.imageView?.let { img ->
            val simpleDraweeView = img as? SimpleDraweeView
            simpleDraweeView?.let { simpleDraweeView ->
                if (imageDisplayParams.placeHolderResourceId > 0) {
                    val builder = GenericDraweeHierarchyBuilder(simpleDraweeView.context.getResources())
                    val hierarchy = builder
                        .setPlaceholderImage(imageDisplayParams.placeHolderResourceId)
                        .build()
                    simpleDraweeView.hierarchy = hierarchy
                }
                if (imageDisplayParams.errorResourceId > 0) {
                    simpleDraweeView.hierarchy.setFailureImage(imageDisplayParams.errorResourceId)
                }
                imageDisplayParams.imageUrl.let { url ->

                    val builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    if (imageDisplayParams.width > 0 && imageDisplayParams.height > 0) {
                        builder.resizeOptions = ResizeOptions(
                            imageDisplayParams.width,
                            imageDisplayParams.height
                        )
                    }
                    val request = builder.build()
                    val controller =
                        Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(simpleDraweeView.controller)
                            .build()
                    simpleDraweeView.controller = controller
                }
            }

        }


    }

    override fun load(img: ImageView, imageUrl: String) {
        val simpleDraweeView = img as? SimpleDraweeView
        simpleDraweeView?.setImageURI(imageUrl)
    }

    override fun load(img: ImageView, imageUrl: String, roundValue: Int) {
        val simpleDraweeView = img as? SimpleDraweeView
        simpleDraweeView?.let {
            val roundingParams = RoundingParams.fromCornersRadius(roundValue.toFloat())
            it.hierarchy.roundingParams = roundingParams
            it.setImageURI(imageUrl)
        }
    }

    override fun load(img: ImageView, imgResourceId: Int) {
        val simpleDraweeView = img as? SimpleDraweeView
        simpleDraweeView?.setActualImageResource(imgResourceId)
    }

    override fun load(img: ImageView, imgResourceId: Int, roundValue: Int) {
        val simpleDraweeView = img as? SimpleDraweeView
        simpleDraweeView?.let {
            val roundingParams = RoundingParams.fromCornersRadius(roundValue.toFloat())
            it.hierarchy.roundingParams = roundingParams
            simpleDraweeView.setActualImageResource(imgResourceId)
        }
    }

    override fun getNetBitmap(
        context: Context,
        imageUrl: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        val imageRequest = ImageRequestBuilder
            .newBuilderWithSource(Uri.parse(imageUrl))
            .setLocalThumbnailPreviewsEnabled(true)
            .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)
            .setProgressiveRenderingEnabled(false)
            .build()

        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.prefetchToDiskCache(imageRequest, context, Priority.HIGH)
        imagePipeline.fetchDecodedImage(imageRequest, context)
            .subscribe(object : BaseBitmapDataSubscriber() {
                override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                    listener.onGetBitmapFinish(null)
                }

                override fun onNewResultImpl(bitmap: Bitmap?) {
                    listener.onGetBitmapFinish(bitmap)
                }
            }, CallerThreadExecutor.getInstance())

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

        val imageRequest = ImageRequestBuilder
            .newBuilderWithSource(Uri.parse(imgUrl))
            .build()
        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.fetchDecodedImage(imageRequest, context)
            .subscribe(object : BaseBitmapDataSubscriber() {
                override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                    listener.onDownloadImageFinish(null)
                }

                override fun onNewResultImpl(bitmap: Bitmap?) {
                    if (bitmap != null) {
                        saveBitmap(bitmap, getPath(context, imgUrl))
                    } else {
                        listener.onDownloadImageFinish(null)
                    }
                }
            }, CallerThreadExecutor.getInstance())


    }

    override fun clearMemory(context: Context) {
        Fresco.getImagePipeline().clearMemoryCaches()
    }

    override fun clearImageCache(context: Context) {
        Fresco.getImagePipeline().clearDiskCaches()
    }


}