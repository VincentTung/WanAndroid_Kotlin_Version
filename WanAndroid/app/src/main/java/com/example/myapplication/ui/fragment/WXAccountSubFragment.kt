package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ArticleListAdapter
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.ArticleData
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.uber.autodispose.autoDisposable


/**
 * 公众号
 */
class WXAccountSubFragment(var title: String, var cid: Int) : BaseFragment() {


    private var mHandler: Handler = Handler()
    private var mPage = 0
    private var mArticles: MutableList<Article> = mutableListOf()
    private var mAdapter: ArticleListAdapter = ArticleListAdapter(mArticles)
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(R.layout.fragment_knowledge_sub, null, false)
        recyclerView = contentView.findViewById(R.id.recyclerView)
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@WXAccountSubFragment.context,
                DividerItemDecoration.VERTICAL
            )
        )
        getArticle(mPage)
        return contentView
    }


    private fun getArticle(page: Int) {


        ApiHelper.mInstance.getApiService().getArticleUnderTree(page, cid).compose(schdulesTransform())
            .autoDisposable(scopeProvider).subscribe(object :
                BaseObserver<ArticleData<List<Article>>, ResultData<ArticleData<List<Article>>>> {
                override fun onSuccess(t: ResultData<ArticleData<List<Article>>>) {
                    if (t.errorCode == 0) {
                        t.data?.datas?.let {
                            if (t.data?.datas!!.isNotEmpty()) {
                                mPage = t.data!!.curPage
                                mArticles.addAll(it)
                                mAdapter.notifyDataSetChanged()
                                if (mPage != 1) {
                                    mHandler.postDelayed({ recyclerView.smoothScrollBy(0, 200) }, 100)
                                }
                            }
                        }
                    }

                }

                override fun onFailed(errorCode: Int) {

                }


            })
    }

}