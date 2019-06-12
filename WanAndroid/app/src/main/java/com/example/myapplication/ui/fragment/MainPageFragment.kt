package com.example.myapplication.ui.fragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


    private val banner_offset_time = 2000
    var mHanlder: Handler = Handler()
    var mPage = 1
    var articles: MutableList<Article> = ArrayList()
    var adapter: ArticleListAdapter = ArticleListAdapter(articles)
    lateinit var nestedScrollView: NestedScrollView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(com.example.myapplication.R.layout.fragment_main_page, null, false)
        nestedScrollView =
            contentView.findViewById<NestedScrollView>(com.example.myapplication.R.id.nestedscrollview)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView
                .setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, ioldScrollY3: Int ->
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        getArticle(mPage + 1)
                    }

                }
        }


        var recyclerview = contentView.findViewById<RecyclerView>(com.example.myapplication.R.id.recyclerView)
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@MainPageFragment.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerview.layoutManager = linearLayoutManager

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
                        this@MainPageFragment.context?.let { it1 ->
                            url?.let { it2 ->
                                WebViewActivity.start(
                                    it1,
                                    it2
                                )
                            }
                        }

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
                        t.data?.datas?.let {
                            if (t.data?.datas!!.isNotEmpty()) {
                                mPage = t.data!!.curPage
                                articles.addAll(it)
                                adapter.notifyDataSetChanged()

                                mHanlder.postDelayed({ nestedScrollView.smoothScrollBy(10, 200) }, 100)


                            }

                        }


                    }
                }

                override fun onComplete() {

                }

            })
    }

    override fun onItemClick(position: Int) {

        this@MainPageFragment.context?.let {
            articles[position].link?.let { it1 ->
                WebViewActivity.start(
                    this@MainPageFragment.context!!,
                    it1
                )
            }
        }
    }


}