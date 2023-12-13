package com.example.countlories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countlories.data.response.Menu
import com.example.countlories.databinding.ItemFavBinding

class MenuAdapter(private val listMenu: ArrayList<Menu>) :
    RecyclerView.Adapter<MenuAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listMenu.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val menu = listMenu[position]

        holder.binding.apply {
            tvTitle.text = menu.name
            calorie.text = "${menu.calories} Kkal"
            Glide.with(ivStory.context)
                .load(menu.image)
                .into(ivStory)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(menu)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(menu : Menu)
    }
}