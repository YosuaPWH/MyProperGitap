package com.yosuahaloho.mypropergitap.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yosuahaloho.mypropergitap.databinding.UserItemListBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import com.yosuahaloho.mypropergitap.ui.detailuser.DetailUserActivity
import com.yosuahaloho.mypropergitap.ui.favorite.FavoriteFragmentDirections
import com.yosuahaloho.mypropergitap.ui.home.HomeFragmentDirections

class ListUserAdapter(private val isHomeFragments: Boolean, private val isFavoriteFragments: Boolean) :
    RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {

    private var items = mutableListOf<User>()

    inner class ListUserViewHolder(val binding: UserItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val viewHolder =
            UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserViewHolder(viewHolder)
    }

    fun submitList(newList: List<User>) {
        val diffCallback = MyDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val data = items[position]

        holder.binding.apply {
            tvUsername.text = data.username
            Glide
                .with(holder.itemView.context)
                .load(data.avatar_url)
                .into(imgAkun)

            root.setOnClickListener {
                if (isHomeFragments) {
                    val toDetailUserActivity =
                        HomeFragmentDirections.actionNavigationHomeToDetailUserActivity()
                    toDetailUserActivity.username = data.username
                    it.findNavController().navigate(toDetailUserActivity)
                } else if (isFavoriteFragments) {
                    val toDetailUserActivity =
                        FavoriteFragmentDirections.actionNavigationFavoriteToDetailUserActivity()
                    toDetailUserActivity.username = data.username
                    it.findNavController().navigate(toDetailUserActivity)
                } else {
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    val bundle = Bundle().apply { putString("username", data.username) }
                    intent.putExtras(bundle)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    private class MyDiffCallback(private val oldList: List<User>, private val newList: List<User>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }
    }
}