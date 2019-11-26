package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemArticleBinding
import com.example.myapplication.entity.Article

class ArticleListAdapter() : PagedListAdapter<Article, ArticleListAdapter.ViewHolder>(object :
    ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}) {

    var onItemListener: OnItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding: ItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false
        );
        return ViewHolder(dataBinding)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemDataBinding.article = getItem (position)
        holder.itemView.setOnClickListener {
            onItemListener?.onItemClick(position)
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(val itemDataBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(itemDataBinding.root)

}
