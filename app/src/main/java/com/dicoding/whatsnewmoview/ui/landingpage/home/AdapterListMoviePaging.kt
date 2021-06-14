package com.dicoding.whatsnewmoview.ui.landingpage.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.core.data.model.ListMovieDto
import com.dicoding.whatsnewmoview.databinding.AdapterHomeMovieBinding
import com.dicoding.whatsnewmoview.ui.base.adapter.BaseHolder
import com.dicoding.whatsnewmoview.ui.base.adapter.RecyclerViewItemClickListener
import javax.inject.Inject


class AdapterListMoviePaging @Inject constructor(val context: Context) :
    PagingDataAdapter<ListMovieDto, RecyclerView.ViewHolder>(
        COMPARATOR
    ) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ListMovieDto>() {
            override fun areItemsTheSame(
                oldItem: ListMovieDto,
                newItem: ListMovieDto
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListMovieDto,
                newItem: ListMovieDto
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    private var listener: RecyclerViewItemClickListener<ListMovieDto>? = null

    fun setOnclickListener(listener: RecyclerViewItemClickListener<ListMovieDto>) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as AdapterListMoviePaging).bind(data as ListMovieDto)
        holder.bindData(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterHomeMovieBinding.inflate(inflater, parent, false)
        return AdapterListMoviePaging(binding)
    }

    inner class AdapterListMoviePaging(
        val binding: AdapterHomeMovieBinding
    ) :
        BaseHolder<ListMovieDto>(binding.root, listener) {

        fun bind(itemData: ListMovieDto?) {
            itemData?.let {
                binding.txtTitle.text = it.title
                binding.desc.text = it.overview
                binding.txtDate.text = it.releaseDate
                binding.txtGenre.text = it.getGenre()
                Glide
                    .with(context)
                    .load(it.posterPath)
                    .centerCrop()
                    .into(binding.imageRoundCorners)
            }
        }
    }

}