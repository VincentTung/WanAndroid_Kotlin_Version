package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.entity.Project

class ProjectListAdapter(var projectList: MutableList<Project>) :
    RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

    var onItemListener: OnItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var article = projectList[position]
        holder.tv_author.text = article.author
        holder.tv_content.text = article.desc
        holder.tv_title.text = article.title
        holder.tv_date.text = article.niceDate

        Glide.with(holder.img_pic.context).load(article.envelopePic).into(holder.img_pic)
        holder.itemView.setOnClickListener {
            onItemListener?.onItemClick(position)
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_author = itemView.findViewById<TextView>(R.id.tv_author)
        var tv_content = itemView.findViewById<TextView>(R.id.tv_content)
        var tv_date = itemView.findViewById<TextView>(R.id.tv_date)
        var tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        var img_pic = itemView.findViewById<ImageView>(R.id.img_pic)
    }

}
