package com.yosuahaloho.mypropergitap.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yosuahaloho.mypropergitap.databinding.UserItemListBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.ui.detailuser.DetailUserActivity
import com.yosuahaloho.mypropergitap.ui.detailuser.DetailUserActivityArgs
import com.yosuahaloho.mypropergitap.ui.home.HomeFragmentDirections

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


            root.setOnClickListener {
                val toDetailUserActivity = HomeFragmentDirections.actionNavigationHomeToDetailUserActivity()
                toDetailUserActivity.username = data.username
                it.findNavController().navigate(toDetailUserActivity)
            }
        }
    }

    override fun getItemCount(): Int = listUser.size
}