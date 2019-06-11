package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.Article
import org.w3c.dom.Text

class ArticleListAdapter(var articles: MutableList<Article>) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_author = itemView.findViewById<TextView>(R.id.tv_author)
        var tv_type = itemView.findViewById<TextView>(R.id.tv_type)
        var tv_date = itemView.findViewById<TextView>(R.id.tv_date)
        var tv_title = itemView.findViewById<TextView>(R.id.tv_title)


    }


    var onItemListenr: OnItemListenr? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleListAdapter.ViewHolder, position: Int) {

        var article = articles[position]
        holder.tv_author.text = article.author
        holder.tv_type.text = article.chapterName
        holder.tv_title.text = article.title
        holder.tv_date.text = article.niceDate

        holder.itemView.setOnClickListener{

            onItemListenr?.onItemClick(position)
        }
    }

    interface OnItemListenr {

        fun onItemClick(position: Int)
    }
}