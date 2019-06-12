package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.Tree
import com.nex3z.flowlayout.FlowLayout

class KnowledgeTreeListAdapter(var treeList: MutableList<Tree>) :
    RecyclerView.Adapter<KnowledgeTreeListAdapter.ViewHolder>() {

    var onItemListener: OnItemListener? = null
    lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_knowledge_tree, parent, false)
        layoutInflater = LayoutInflater.from(view.context)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return treeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var article = treeList[position]
        holder.tv_name.text = article.name
        holder.tv_children.removeAllViews()
        article.children?.forEach {

            var tx: TextView = layoutInflater.inflate(R.layout.item_tree_child, null, false) as TextView
            tx.text = it.name
            var layoutParams: ViewGroup.MarginLayoutParams =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(10, 10, 10, 10)
            tx.layoutParams = layoutParams

            holder.tv_children.addView(tx)

        }


        holder.itemView.setOnClickListener {

            onItemListener?.onItemClick(position)
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name = itemView.findViewById<TextView>(R.id.tv_name)
        var tv_children = itemView.findViewById<FlowLayout>(R.id.tv_children)

    }

}
