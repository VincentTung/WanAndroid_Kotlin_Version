package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R.layout
import com.example.myapplication.adapter.KnowledgeTreeListAdapter
import com.example.myapplication.entity.KnowledgeTree
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.uber.autodispose.autoDisposable


/**
 * 知识体系
 */
class KnowledgeTreeFragment : BaseFragment(), KnowledgeTreeListAdapter.OnItemListener {
    override fun onItemClick(position: Int) {

    }


    private lateinit var mAdapter: KnowledgeTreeListAdapter
    private var mTreeList = mutableListOf<KnowledgeTree>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(layout.fragment_knowledge_tree, null, false)
        var recyclerview = contentView.findViewById<RecyclerView>(com.example.myapplication.R.id.recyclerView)


        mAdapter = KnowledgeTreeListAdapter(mTreeList)
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@KnowledgeTreeFragment.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerview.layoutManager = linearLayoutManager

        mAdapter.onItemListener = this
        recyclerview.adapter = mAdapter
        recyclerview.setHasFixedSize(false)


        ApiHelper.mInstance.getApiService().getKnowledgeTree().compose(schdulesTransform())
            .autoDisposable(scopeProvider).subscribe(object :
                BaseObserver<List<KnowledgeTree>, ResultData<List<KnowledgeTree>>> {
                override fun onFailed(errorCode: Int) {

                }

                override fun onSuccess(t: ResultData<List<KnowledgeTree>>) {


                    t.data?.let {
                        mTreeList.addAll(it)

                        mAdapter.notifyDataSetChanged()
                    }
                }
            })
        return contentView
    }


}