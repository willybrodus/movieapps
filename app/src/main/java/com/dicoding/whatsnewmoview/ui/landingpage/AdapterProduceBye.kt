package com.dicoding.whatsnewmoview.ui.landingpage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.company.core.data.model.ProduceByDto
import com.dicoding.whatsnewmoview.databinding.AdapterProductionByBinding
import com.dicoding.whatsnewmoview.ui.base.adapter.BaseAdapter
import com.dicoding.whatsnewmoview.ui.base.adapter.BaseHolder
import com.dicoding.whatsnewmoview.ui.base.adapter.RecyclerViewItemClickListener
import javax.inject.Inject

class AdapterProduceBye @Inject constructor(val context: Context) : BaseAdapter<ProduceByDto, AdapterProduceBye.ViewHolder>() {

    override fun bindViewHolder(holder: ViewHolder, data: ProduceByDto?) {
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProductionByBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    inner class ViewHolder(val  binding: AdapterProductionByBinding, listener: RecyclerViewItemClickListener<ProduceByDto>?) :
        BaseHolder<ProduceByDto>(binding.root, listener) {
        fun bind(itemData: ProduceByDto?) {
            itemData?.let { data ->
                binding.txtName.text = itemData.name
                binding.txtLocation.text = itemData.originCountry
                Glide
                    .with(context)
                    .load(data.logoPath)
                    .into(binding.imgLogoCompanies)
            }
        }
    }

}