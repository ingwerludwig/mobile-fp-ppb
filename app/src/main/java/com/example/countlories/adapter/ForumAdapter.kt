package com.example.countlories.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countlories.data.response.Blog
import com.example.countlories.data.response.Forum
import com.example.countlories.databinding.ItemForumBinding
import com.example.countlories.databinding.ItemTipsBinding
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ForumAdapter (private val listForum: ArrayList<Forum>) :
    RecyclerView.Adapter<ForumAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemForumBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemForumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listForum.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val forum = listForum[position]

        holder.binding.apply {
            tvTitle.text = forum.title
            val dateString = forum.createdAt
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = LocalDate.parse(dateString, inputFormat)

            val outputFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            val formattedDate = outputFormat.format(date)
            create.text = formattedDate
            commentCount.text = forum.CommentCount.toString()
            Glide.with(ivStory.context)
                .load(forum.image)
                .fitCenter()
                .into(ivStory)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(forum)
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(forum: Forum)
    }
}