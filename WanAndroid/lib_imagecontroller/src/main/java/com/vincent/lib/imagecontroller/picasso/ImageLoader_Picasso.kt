package com.vincent.lib.imagecontroller.picasso

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.squareup.picasso.Transformation
import com.vincent.lib.imagecontroller.ImageDisplayParams
import com.vincent.lib.imagecontroller.ImageLoader
import com.vincent.lib.imagecontroller.util.BitmapUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ImageLoader_Picasso : ImageLoader {
    override fun init() {

    }

    override fun load(imageDisplayParams: ImageDisplayParams) {
    }

    override fun load(img: ImageView, imageUrl: String) {
        Picasso.get().load(imageUrl).into(img)
    }

    override fun load(img: ImageView, imageUrl: String, roundValue: Int) {
        Picasso.get().load(imageUrl).transform(CircleTransform(roundValue, 0)).into(img)
    }

    override fun load(img: ImageView, imgResourceId: Int) {
        Picasso.get().load(imgResourceId).into(img)
    }

    override fun load(img: ImageView, imgResourceId: Int, roundValue: Int) {
        Picasso.get().load(imgResourceId).transform(CircleTransform(roundValue, 0)).into(img)
    }

    override fun getNetBitmap(
        context: Context,
        imageUrl: String,
        listener: ImageLoader.GetBitmapListener
    ) {
        Picasso.get().load(imageUrl).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                listener.onGetBitmapFinish(null)
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                listener.onGetBitmapFinish(bitmap)
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
        Picasso.get().load(imgUrl).into(getTarget(BitmapUtil.getPath(context, imgUrl), listener))
    }

    override fun clearMemory(context: Context) {
    }

    override fun clearImageCache(context: Context) {
    }

    private fun getTarget(path: String, listener: ImageLoader.DownloadImageListener): Target {
        return object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                listener.onDownloadImageFinish(null)
            }

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                Thread(Runnable {
                    val file = File(path)
                    try {
                        file.createNewFile()
                        val ostream = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream)
                        ostream.flush()
                        ostream.close()
                        listener.onDownloadImageFinish(path)
                    } catch (e: IOException) {
                        Log.e(TAG, e.getLocalizedMessage())
                        listener.onDownloadImageFinish(null)
                    }
                }).start()

            }

        }
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

        val requestCreator = Picasso.get().load(imgUrl)
        if ((width > 0 && height > 0) || roundValue > 0) {
            requestCreator.transform(object : Transformation {
                override fun transform(bitmap: Bitmap): Bitmap {


                    var result: Bitmap = bitmap
                    if (width > 0 && height > 0) {
                        val aspectRatio = bitmap.height.toDouble() / bitmap.width.toDouble()
                        val targetHeight = (width * aspectRatio).toInt()
                        result = Bitmap.createScaledBitmap(bitmap, width, targetHeight, false)
                    }

                    if (roundValue > 0) {

                        val source: Bitmap = result
                        val paint = Paint()

                        paint.isAntiAlias = true
                        paint.shader = BitmapShader(
                            source, Shader.TileMode.CLAMP,
                            Shader.TileMode.CLAMP
                        )

                        val output = Bitmap.createBitmap(
                            source.width, source.height,
                            Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(output)
                        canvas.drawRoundRect(
                            RectF(
                                0F, 0F, source.width.toFloat(),
                                source.height.toFloat()
                            ), roundValue.toFloat(), roundValue.toFloat(), paint
                        )

                        if (source != output) {
                            source.recycle()
                        }
                    }
                    return result
                }

                override fun key(): String {
                    return "load_params$imgUrl"
                }
            })
        }
        if (errorResourceId > 0) {
            requestCreator.error(errorResourceId)
        }
        if (placeHolderResourceId > 0) {
            requestCreator.placeholder(placeHolderResourceId)
        }

        requestCreator.into(img)

    }

    companion object {
        const val TAG = "ImageLoader_Picasso"
    }
}