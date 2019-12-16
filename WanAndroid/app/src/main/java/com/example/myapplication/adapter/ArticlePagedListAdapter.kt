package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.app.WanApplication
import com.example.myapplication.databinding.ItemArticleBinding
import com.example.myapplication.entity.Article
import com.example.myapplication.ui.activity.WebViewActivity

class ArticleClickHandler {

    fun onItemClick(article: Article) {
            article.link?.let { url ->
                WebViewActivity.start(
                    WanApplication.mInstance!!,
                    url
                )

        }
    }
}

class ArticlePagedListAdapter : PagedListAdapter<Article, ArticlePagedListAdapter.ViewHolder>(object :
    ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}) {

    private val mClickHandler: ArticleClickHandler = ArticleClickHandler()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding: ItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false
        )
        return ViewHolder(dataBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemDataBinding.article = getItem(position)
        holder.itemDataBinding.clickHandler = mClickHandler
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }
    class ViewHolder(val itemDataBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(itemDataBinding.root)

}

