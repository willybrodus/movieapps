package com.dicoding.whatsnewmoview.ui.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.core.databinding.LayoutLoadingAdapterRecylerViewBinding
import com.dicoding.whatsnewmoview.R
import javax.inject.Inject

class LoadingRecylerViewAdapter @Inject constructor(): LoadStateAdapter<LoadingRecylerViewAdapter.LoadingStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_loading_adapter_recyler_view, parent, false)

        val binding = LayoutLoadingAdapterRecylerViewBinding.bind(view)
        return LoadingStateViewHolder(
            binding
        )
    }

    inner class LoadingStateViewHolder(
        private val binding: LayoutLoadingAdapterRecylerViewBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.txtErrorMessage.isVisible = true
            }
            binding.progressbar.isVisible = loadState is LoadState.Loading
        }
    }
}