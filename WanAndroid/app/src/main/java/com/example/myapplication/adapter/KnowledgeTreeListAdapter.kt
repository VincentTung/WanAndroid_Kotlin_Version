package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.KnowledgeTree

class KnowledgeTreeListAdapter(var articles: MutableList<KnowledgeTree>) :
    RecyclerView.Adapter<KnowledgeTreeListAdapter.ViewHolder>() {

    var onItemListener: OnItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_knowledge_tree, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var article = articles[position]
        holder.tv_name.text = article.name
        holder.tv_children.text = article.children?.get(0)?.name


        holder.itemView.setOnClickListener {

            onItemListener?.onItemClick(position)
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name = itemView.findViewById<TextView>(R.id.tv_name)
        var tv_children = itemView.findViewById<TextView>(R.id.tv_children)

    }

}
