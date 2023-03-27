package com.yosuahaloho.mypropergitap.utils

import androidx.recyclerview.widget.DiffUtil
import com.yosuahaloho.mypropergitap.repos.model.User

class MyDiffCallback(private val oldList: List<User>, private val newList: List<User>) : DiffUtil.Callback() {

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