package com.vincent.funny.kotlin.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.adapter.ArticlePagedListAdapter
import com.vincent.funny.kotlin.adapter.ProjectListAdapter
import com.vincent.funny.kotlin.entity.Project
import com.vincent.funny.kotlin.ui.activity.WebViewActivity
import com.vicnent.lib.base.util.exViewModel
import com.vincent.funny.kotlin.viewmodels.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_knowledge_sub.*


/**
 * 项目-子Fragment
 */
class ProjectSubFragment : BaseFragment(), ArticlePagedListAdapter.OnItemListener,
    ProjectListAdapter.OnItemListener {
    override fun onItemClick(position: Int) {
        mProjects[position].link?.let {
            this@ProjectSubFragment.context?.let { it1 ->
                WebViewActivity.start(
                    it1,
                    it
                )
            }
        }
    }

    companion object {

        fun newInstance(cid: Int): ProjectSubFragment {
            val fragment = ProjectSubFragment()
            val  bundle = Bundle()
            bundle.putInt("cid", cid)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var cid: Int = 0
    private val mHandler: Handler = Handler()
    private var mPage = 0
    private var mProjects: MutableList<Project> = mutableListOf()
    private var mAdapter: ProjectListAdapter = ProjectListAdapter(mProjects)

    private lateinit var mViewModel: ProjectViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cid = arguments?.getInt("cid")!!
        mViewModel =exViewModel(ProjectViewModel::class.java)
        return inflater.inflate(R.layout.fragment_knowledge_sub, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.onItemListener = this
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    this@ProjectSubFragment.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        mViewModel.observeProjectSubList().observe(this, Observer {
            mProjects.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
        mViewModel.getProjectSubList(mPage, cid)
    }

}