package com.vincent.funny.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.databinding.ItemArticleBinding

import com.vincent.funny.kotlin.entity.Article

class ArticleListAdapter :
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    private val mClickHandler: ArticleClickHandler = ArticleClickHandler()
    var onItemListener: OnItemListener? = null
    private var articleList: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding: ItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false
        )
        return ViewHolder(dataBinding)

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemBinding.article = articleList[position]
        holder.itemView.setOnClickListener {
            onItemListener?.onItemClick(position)
        }
    }

    fun submitList(list: List<Article>) {
        var length = list.size
        if (length == 0) return
        var index = articleList.size

        articleList.addAll(list)
        notifyItemRangeInserted(index + 1, length)
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(val itemBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}
