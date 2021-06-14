package com.dicoding.whatsnewmoview.ui.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseHolder<T> constructor(itemView: View, private val listener: RecyclerViewItemClickListener<T>? = null): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var itemPosition: Int = 0
    private var itemData: T? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        listener?.itemClick(itemData, view.id)
    }

    fun bindData(position: Int, data: T?) {
        itemPosition = position
        itemData = data
    }

    fun bindData(data: T?) {
        itemData = data
    }

    fun getData(): T? = itemData

    fun getDataPosition(): Int = itemPosition
}