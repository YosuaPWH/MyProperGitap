package com.yosuahaloho.mypropergitap.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yosuahaloho.mypropergitap.databinding.LastButtonBinding
import com.yosuahaloho.mypropergitap.databinding.UserItemListBinding
import com.yosuahaloho.mypropergitap.repos.model.User

class ListUserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = arrayListOf<User>()
    private lateinit var onItemClickCallback: OnUserClickCallback
    private lateinit var onItemLoadMoreCallback: OnLoadMoreClickCallBack
    var isLoadMoreUser = false

    inner class ListUserViewHolder(val binding: UserItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LoadMoreButtonViewHolder(val binding: LastButtonBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(list: List<User>) {
        items = list as ArrayList<User>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            REGULAR_ITEM -> {
                val viewHolder =
                    UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListUserViewHolder(viewHolder)
            }
            ADD_BUTTON_ITEM -> {
                val addButtonViewHolder =
                    LastButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadMoreButtonViewHolder(addButtonViewHolder)
            }
            else -> throw IllegalArgumentException("Tidak diketahui")
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < items.size) {
            REGULAR_ITEM
        } else {
            ADD_BUTTON_ITEM
        }
    }

    override fun getItemCount(): Int = if (isLoadMoreUser) items.size + 1 else items.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ListUserViewHolder && position < items.size) {
            val data = items[position]

            holder.binding.apply {
                tvUsername.text = data.username
                Glide
                    .with(holder.itemView.context)
                    .load(data.avatar_url)
                    .into(imgAkun)

                root.setOnClickListener {
                    onItemClickCallback.onUserClicked(data)
                }
            }
        } else if (holder is LoadMoreButtonViewHolder) {
            if (isLoadMoreUser) {
                holder.binding.btnLoadMore.visibility = View.VISIBLE

                holder.binding.btnLoadMore.setOnClickListener {
                    onItemLoadMoreCallback.onLoadMoreClicked()
                }
            } else {
                holder.binding.btnLoadMore.visibility = View.GONE
            }


        }

    }


    interface OnUserClickCallback {
        fun onUserClicked(data: User)
    }

    fun setOnUserClickCallback(onUserClickCallback: OnUserClickCallback) {
        this.onItemClickCallback = onUserClickCallback
    }

    interface OnLoadMoreClickCallBack {
        fun onLoadMoreClicked()
    }

    fun setLoadMoreClickCallback(onLoadMoreClickCallBack: OnLoadMoreClickCallBack) {
        this.onItemLoadMoreCallback = onLoadMoreClickCallBack
    }

    companion object {
        private const val REGULAR_ITEM = 0
        private const val ADD_BUTTON_ITEM = 1
    }
}