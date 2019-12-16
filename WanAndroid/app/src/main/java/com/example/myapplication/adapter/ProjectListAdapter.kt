package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemProjectBinding
import com.example.myapplication.entity.Project

class ProjectListAdapter(private var projectList: MutableList<Project>) :
    RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

    var onItemListener: OnItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mDataBinding = DataBindingUtil.inflate<ItemProjectBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_project,
            parent,
            false
        )
        return ViewHolder(mDataBinding)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemBinding.project = projectList[position]
        holder.itemView.setOnClickListener {
            onItemListener?.onItemClick(position)
        }
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(val itemBinding: ItemProjectBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}
