package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ArticleListAdapter
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.PageData
import com.example.myapplication.entity.ResultData
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.example.myapplication.util.exView
import com.example.myapplication.util.exViewModel
import com.example.myapplication.viewmodels.WXAccountSubFragmentViewModel
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_knowledge_sub.*


/**
 * 公众号
 */
class WXAccountSubFragment : BaseFragment(), ArticleListAdapter.OnItemListener {

    private var cid: Int = 0

    companion object {

        fun newInstance(cid: Int): WXAccountSubFragment {

            var fragment = WXAccountSubFragment()
            var bundle = Bundle()
            bundle.putInt("cid", cid)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onItemClick(position: Int) {
        mArticles[position].link?.let {
            this@WXAccountSubFragment.context?.let { it1 ->
                WebViewActivity.start(
                    it1,
                    it
                )
            }
        }
    }


    private var mHandler: Handler = Handler()
    private var mPage = 0
    private var mArticles: MutableList<Article> = mutableListOf()
    private var mAdapter: ArticleListAdapter = ArticleListAdapter()

    private lateinit var mViewModel: WXAccountSubFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cid = arguments?.getInt("cid")!!
        mViewModel = exViewModel(WXAccountSubFragmentViewModel::class.java)
        return inflater.inflate(R.layout.fragment_knowledge_sub, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    this@WXAccountSubFragment.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        mViewModel.observeArticles().observe(this, Observer<PagedList<Article>> {
            mAdapter.submitList(it)
            //todo page统一传递问题
            if (
                it.config.pageSize <= 20) {
                mHandler.postDelayed(
                    { recyclerView.smoothScrollBy(0, 200) },
                    100
                )
            }
        })
    }

}