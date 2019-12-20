package com.vincent.lib.imagecontroller.util

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class BitmapUtil {
    companion object {
        fun saveBitmap(bitmap: Bitmap, path: String): Boolean {
            return try {
                val file = File(path)
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun getPath(context: Context, url: String): String {

            val cacheDir =
                File(context.cacheDir.absolutePath )
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val index = url.lastIndexOf(File.separator) + 1
            return cacheDir.absolutePath + File.separator + url.subSequence(
                index,
                url.length
            )
        }
    }

}