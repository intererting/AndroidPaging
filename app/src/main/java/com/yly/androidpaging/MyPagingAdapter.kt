package com.yly.androidpaging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_test.view.*

class MyPagingAdapter : PagedListAdapter<String, MyPagingAdapter.MyPagingHolder>(VALUE_DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPagingHolder {
        return MyPagingHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_test,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyPagingHolder, position: Int) {
        holder.itemView.text.text = getItem(position)
        println("onBindViewHolder  $position")
    }


    class MyPagingHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    companion object {
        val VALUE_DIFF = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}

