package com.vincent.lib.imagecontroller.glide
import com.bumptech.glide.ListPreloader.PreloadModelProvider
import com.bumptech.glide.RequestBuilder
import java.util.*
import java.util.Collections.singletonList
import java.util.Collections.emptyList
import android.text.TextUtils
import com.bumptech.glide.Glide


//class Preloader: PreloadModelProvider<String> {
//
//    private val myUrls:List<String> = listOf()
//    override fun getPreloadItems(position: Int): MutableList<String> {
//        val url = myUrls[position]
//        return if (TextUtils.isEmpty(url)) {
//            emptyList()
//        } else singletonList(url)
//    }
//
//    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*>? {
//
//        return Glide.with()
//    }
//
//}