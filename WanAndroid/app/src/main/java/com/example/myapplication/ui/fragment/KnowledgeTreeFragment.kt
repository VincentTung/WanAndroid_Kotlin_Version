package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.KnowledgeTreeListAdapter
import com.example.myapplication.entity.Tree
import com.example.myapplication.repository.KnowledgeRepository
import com.example.myapplication.viewmodels.KnowledgeViewModelFactory
import com.example.myapplication.ui.activity.KnowledgeTreeActivity
import com.example.myapplication.viewmodels.KnowledgeViewModel


/**
 * 知识体系
 */
class KnowledgeTreeFragment : BaseFragment(), KnowledgeTreeListAdapter.OnItemListener {
    override fun onItemClick(position: Int) {

        this@KnowledgeTreeFragment.context?.let {
            KnowledgeTreeActivity.start(
                it,
                mTreeList[position]
            )
        }
    }


    private lateinit var mAdapter: KnowledgeTreeListAdapter
    private var mTreeList = mutableListOf<Tree>()

    private lateinit var mViewModel: KnowledgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var contentView = inflater.inflate(R.layout.fragment_knowledge_tree, null, false)
        var recyclerview =
            contentView.findViewById<RecyclerView>(com.example.myapplication.R.id.recyclerView)

        mViewModel = ViewModelProviders.of(
            this,
            KnowledgeViewModelFactory(KnowledgeRepository())
        ).get(KnowledgeViewModel::class.java)
        mAdapter = KnowledgeTreeListAdapter(mTreeList)
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerview.layoutManager = linearLayoutManager

        mAdapter.onItemListener = this
        recyclerview.adapter = mAdapter
        recyclerview.setHasFixedSize(false)

        mViewModel.observeKnowledgeTrees().observe(this, Observer<List<Tree>> {
            mAdapter.treeList.addAll(it)
            mAdapter.notifyDataSetChanged()
        })

        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTree()
    }

    private fun getTree() {
        mViewModel.getKnowledgeTrees()
    }


}