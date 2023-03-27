package com.yosuahaloho.mypropergitap.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yosuahaloho.mypropergitap.databinding.LastButtonBinding
import com.yosuahaloho.mypropergitap.databinding.UserItemListBinding
import com.yosuahaloho.mypropergitap.repos.model.User
import timber.log.Timber

class ListUserAdapter(
    private val isHomeFragments: Boolean,
    private val isFavoriteFragments: Boolean
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<User>()
    private lateinit var onItemClickCallback: OnUserClickCallback

    inner class ListUserViewHolder(val binding: UserItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LoadMoreButtonViewHolder(val binding: LastButtonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            REGULAR_ITEM -> {
                val viewHolder = UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListUserViewHolder(viewHolder)
            }
            ADD_BUTTON_ITEM -> {
                val addButtonViewHolder = LastButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun submitList(newList: List<User>) {
        val diffCallback = MyDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ListUserViewHolder) {
            val data = items[position]
            holder.binding.apply {
                tvUsername.text = data.username
                Glide
                    .with(holder.itemView.context)
                    .load(data.avatar_url)
                    .into(imgAkun)

                root.setOnClickListener {
                    onItemClickCallback.onUserClicked(data, isHomeFragments, isFavoriteFragments)
                }
            }
        } else if (holder is LoadMoreButtonViewHolder) {
            holder.binding.btnLoadMore.setOnClickListener {
                Timber.d("DIKLIK")
            }
        }

    }

    override fun getItemCount(): Int = items.size + 1

    interface OnUserClickCallback {
        fun onUserClicked(data: User, isHomeFragments: Boolean, isFavoriteFragments: Boolean)
    }

    fun setOnUserClickCallback(onUserClickCallback: OnUserClickCallback) {
        this.onItemClickCallback = onUserClickCallback
    }

    companion object {
        private const val REGULAR_ITEM = 0
        private const val ADD_BUTTON_ITEM = 1
    }
}