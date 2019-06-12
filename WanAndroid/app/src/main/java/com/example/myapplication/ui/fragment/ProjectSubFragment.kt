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
import com.example.myapplication.adapter.ProjectListAdapter
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.PageData
import com.example.myapplication.entity.Project
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.uber.autodispose.autoDisposable


/**
 * 项目-子Fragment
 */
class ProjectSubFragment( var cid: Int) : BaseFragment(), ArticleListAdapter.OnItemListener,
    ProjectListAdapter.OnItemListener {
    override fun onItemClick(position: Int) {
        mArticles[position].link?.let { this@ProjectSubFragment.context?.let { it1 -> WebViewActivity.start(it1, it) } }
    }


    private var mHandler: Handler = Handler()
    private var mPage = 0
    private var mArticles: MutableList<Project> = mutableListOf()
    private var mAdapter: ProjectListAdapter = ProjectListAdapter(mArticles)
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(R.layout.fragment_knowledge_sub, null, false)
        recyclerView = contentView.findViewById(R.id.recyclerView)
        recyclerView.adapter = mAdapter
        mAdapter.onItemListener = this
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@ProjectSubFragment.context,
                DividerItemDecoration.VERTICAL
            )
        )
        getArticle(mPage)
        return contentView
    }


    private fun getArticle(page: Int) {


        ApiHelper.mInstance.getApiService().getProjectSubList(page, cid).compose(schdulesTransform())
            .autoDisposable(scopeProvider).subscribe(object :
                BaseObserver<PageData<List<Project>>, ResultData<PageData<List<Project>>>> {
                override fun onSuccess(t: ResultData<PageData<List<Project>>>) {
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