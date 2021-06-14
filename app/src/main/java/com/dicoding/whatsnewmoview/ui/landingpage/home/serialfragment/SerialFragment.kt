package com.dicoding.whatsnewmoview.ui.landingpage.home.serialfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.core.data.model.ListMovieDto
import com.dicoding.whatsnewmoview.databinding.FragmentSerialBinding
import com.dicoding.whatsnewmoview.ui.base.BaseFragment
import com.dicoding.whatsnewmoview.ui.base.adapter.LoadingRecylerViewAdapter
import com.dicoding.whatsnewmoview.ui.base.adapter.RecyclerViewItemClickListener
import com.dicoding.whatsnewmoview.ui.landingpage.detailserial.DetailSerialActivity
import com.dicoding.whatsnewmoview.ui.landingpage.home.AdapterListMoviePaging
import com.dicoding.whatsnewmoview.util.ext.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SerialFragment : BaseFragment() {

    private lateinit var viewModel: SerialViewModel
    private lateinit var binding: FragmentSerialBinding

    @Inject
    lateinit var adapterSerial: AdapterListMoviePaging

    @Inject
    lateinit var adapterLoading: LoadingRecylerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSerialBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecylerView()
        initSwipeRefreshListener()
        viewModel.getListSerial()
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SerialViewModel::class.java)
    }


    override fun observeChange() {
        observe(viewModel.throwable, ::onErrorHandle)
        observe(viewModel.dataSerial, ::submiteData)
    }

    private fun setRecylerView() {
        val llManager = LinearLayoutManager(activity?.applicationContext)
        binding.rvList.layoutManager = llManager
        binding.rvList.adapter = adapterSerial.withLoadStateFooter(
            footer = adapterLoading
        )

        adapterSerial.addLoadStateListener { loadState ->

            binding.pbLoading.isVisible = loadState.source.refresh is LoadState.Loading

            showEmptyPage(loadState.source.refresh is LoadState.NotLoading && loadState.prepend.endOfPaginationReached && adapterSerial.itemCount == 0)

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(context, it.error.message, Toast.LENGTH_LONG).show()
            }
        }

        adapterSerial.setOnclickListener(object : RecyclerViewItemClickListener<ListMovieDto> {
            override fun itemClick(item: ListMovieDto?, state: Int) {
                item?.let {
                    startActivity(DetailSerialActivity.getIntent(getBaseActivity(), it))
                }
            }
        })
    }

    private fun submiteData(data: PagingData<ListMovieDto>) {
        binding.swipeLayout.isRefreshing = false
        lifecycleScope.launch {
            adapterSerial.submitData(data)
        }
    }

    private fun showEmptyPage(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvList.visibility = View.GONE
            binding.emptyDataSerial.visibility = View.VISIBLE
        } else {
            binding.rvList.visibility = View.VISIBLE
            binding.emptyDataSerial.visibility = View.GONE
        }
    }

    @ExperimentalCoroutinesApi
    private fun initSwipeRefreshListener() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getListSerial()
        }
    }

}