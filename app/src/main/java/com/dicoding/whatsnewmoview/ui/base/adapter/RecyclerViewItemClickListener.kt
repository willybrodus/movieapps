package com.dicoding.whatsnewmoview.ui.base.adapter

import androidx.annotation.IdRes

interface RecyclerViewItemClickListener<in T> {
    fun itemClick(item: T?, @IdRes viewId: Int)
}