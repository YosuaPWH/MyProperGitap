package com.yosuahaloho.mypropergitap.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yosuahaloho.mypropergitap.databinding.UserItemListBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import kotlinx.coroutines.withContext

class ListUserAdapter(private val listUser: List<User>) : RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {

    inner class ListUserViewHolder(val binding: UserItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val viewHolder = UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val data = listUser[position]

        holder.binding.apply {
            tvUsername.text = data.username

            Glide
                .with(holder.itemView.context)
                .load(data.avatar_url)
                .into(imgAkun)

        }
    }

    override fun getItemCount(): Int = listUser.size
}