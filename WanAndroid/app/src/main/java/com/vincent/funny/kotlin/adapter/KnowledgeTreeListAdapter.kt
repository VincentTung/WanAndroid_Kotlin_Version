package com.vincent.funny.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.databinding.ItemKnowledgeTreeBinding
import com.vincent.funny.kotlin.entity.Tree

class KnowledgeTreeListAdapter(var treeList: MutableList<Tree>) :
    RecyclerView.Adapter<KnowledgeTreeListAdapter.ViewHolder>() {

    var onItemListener: OnItemListener? = null
    lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding: ItemKnowledgeTreeBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_knowledge_tree,
            parent,
            false
        )
        return ViewHolder(dataBinding)

    }

    override fun getItemCount(): Int {
        return treeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tree = treeList[position]

        holder.itemBiding.tree = tree
        tree.children?.forEach {

            val tx: TextView =
                layoutInflater.inflate(R.layout.item_tree_child, null, false) as TextView
            tx.text = it.name
            val layoutParams: ViewGroup.MarginLayoutParams =
                ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            layoutParams.setMargins(10, 10, 10, 10)
            tx.layoutParams = layoutParams

            holder.itemBiding.tvChildren.addView(tx)

        }


        holder.itemView.setOnClickListener {

            onItemListener?.onItemClick(position)
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(val itemBiding: ItemKnowledgeTreeBinding) :
        RecyclerView.ViewHolder(itemBiding.root)

}
