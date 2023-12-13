package com.example.countlories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countlories.data.response.Blog
import com.example.countlories.databinding.ItemTipsBinding

class BlogAdapter(private val listTips: ArrayList<Blog>) :
    RecyclerView.Adapter<BlogAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemTipsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listTips.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val tip = listTips[position]

        holder.binding.apply {
            tipsTitle.text = tip.title
            Glide.with(tipsImg.context)
                .load(tip.image)
                .fitCenter()
                .into(tipsImg)

        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(tip)
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(blog : Blog)
    }

}