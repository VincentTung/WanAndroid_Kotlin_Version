package com.example.myapplication.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ArticleListAdapter
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.ArticleData
import com.example.myapplication.entity.Banner
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BannerImageLoader
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main_page.*


/**
 * 首页
 */
class MainPageFragment : BaseFragment(), ArticleListAdapter.OnItemListenr {
    override fun onItemClick(position: Int) {

        this@MainPageFragment.context?.let { articles[position].link?.let { it1 ->
            WebViewActivity.start(
                this@MainPageFragment.context!!,
                it1
            )
        } }
    }

    private val banner_offset_time = 2000
    var mPage = 1
    var articles: MutableList<Article> = ArrayList()
    var adapter: ArticleListAdapter = ArticleListAdapter(articles)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(R.layout.fragment_main_page, null, false)

        var recyclerview = contentView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter.onItemListenr = this
        recyclerview.adapter = adapter
        recyclerview.setHasFixedSize(true)
        getBanner()
        getArticle(mPage)

        return contentView
    }

    private fun getBanner() {

        ApiHelper.mInstance.getApiService().getBanner().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object :
                Observer<ResultData<List<Banner>>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.e("Main", e.toString())
                }

                override fun onNext(result: ResultData<List<Banner>>) {
                    var images: MutableList<String> = ArrayList()
                    //to-do
                    result.data?.forEach {
                        it.imagePath?.let { it1 -> images.add(it1) }
                    }
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    banner.setImageLoader(BannerImageLoader())
                    banner.setImages(images)
                    banner.setBannerAnimation(Transformer.DepthPage)
                    banner.isAutoPlay(true)
                    banner.setDelayTime(banner_offset_time)
                    banner.setOnBannerListener {
                        var url = result.data?.get(it)?.url
                        this@MainPageFragment.context?.let { it1 -> url?.let { it2 -> WebViewActivity.start(it1, it2) } }

                    }
                    banner.setIndicatorGravity(BannerConfig.CENTER)
                    banner.start()
                }

                override fun onComplete() {

                }

            })
    }


    private fun getArticle(page: Int) {

        ApiHelper.mInstance.getApiService().getArticle(page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object :
                Observer<ResultData<ArticleData<List<Article>>>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Log.e("Main", e.toString())
                }

                override fun onNext(t: ResultData<ArticleData<List<Article>>>) {

                    if (t.errorCode == 0) {
                        t.data?.datas?.let { articles.addAll(it) }

                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onComplete() {

                }

            })
    }


}