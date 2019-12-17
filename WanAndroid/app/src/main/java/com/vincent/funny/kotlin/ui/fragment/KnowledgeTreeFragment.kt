package com.vincent.funny.kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.adapter.KnowledgeTreeListAdapter
import com.vincent.funny.kotlin.databinding.FragmentKnowledgeTreeBinding
import com.vincent.funny.kotlin.entity.Tree
import com.vincent.funny.kotlin.repository.KnowledgeRepository
import com.vincent.funny.kotlin.ui.activity.KnowledgeTreeActivity
import com.vicnent.lib.base.util.exViewModel
import com.vincent.funny.kotlin.viewmodels.KnowledgeViewModel
import com.vincent.funny.kotlin.viewmodels.KnowledgeViewModelFactory


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


    private lateinit var mBinding: FragmentKnowledgeTreeBinding
    private lateinit var mAdapter: KnowledgeTreeListAdapter
    private var mTreeList = mutableListOf<Tree>()

    private lateinit var mViewModel: KnowledgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_knowledge_tree, container, false)
        mViewModel = exViewModel(
            KnowledgeViewModelFactory(KnowledgeRepository()), KnowledgeViewModel::class.java
        )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mAdapter = KnowledgeTreeListAdapter(mTreeList)
        mAdapter.onItemListener = this

        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
            setHasFixedSize(false)
        }

        mViewModel.observeKnowledgeTrees().observe(this, Observer<List<Tree>> {
            mAdapter.treeList.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
        getTree()
    }

    private fun getTree() {
        mViewModel.getKnowledgeTrees()
    }


}