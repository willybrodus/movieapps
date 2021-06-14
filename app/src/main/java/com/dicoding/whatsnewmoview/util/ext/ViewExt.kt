package com.dicoding.whatsnewmoview.util.ext

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

fun Context.color(resource: Int): Int {
    return ContextCompat.getColor(this, resource)
}

fun Fragment.color(resource: Int): Int {
    context?.let {
        return ContextCompat.getColor(it, resource)
    }
    return 0
}


fun RecyclerView.ViewHolder.color(@ColorRes resource: Int): Int {
    return itemView.context.color(resource)
}


