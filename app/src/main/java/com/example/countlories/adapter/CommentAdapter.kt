package com.example.countlories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countlories.data.response.Comments
import com.example.countlories.databinding.ItemCommentBinding


class CommentAdapter(private val listComment: ArrayList<Comments>) :
    RecyclerView.Adapter<CommentAdapter.ListViewHolder>(){

    class ListViewHolder(var binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val comment = listComment[position]

        holder.binding.apply {
            usernameComment.text = comment.user.name
            comments.text = comment.comment
        }
    }

    override fun getItemCount(): Int = listComment.size
}