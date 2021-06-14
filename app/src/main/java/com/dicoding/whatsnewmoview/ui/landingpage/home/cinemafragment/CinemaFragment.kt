package com.dicoding.whatsnewmoview.ui.landingpage.home.cinemafragment

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
import com.dicoding.whatsnewmoview.databinding.FragmentCinemaBinding
import com.dicoding.whatsnewmoview.ui.base.BaseFragment
import com.dicoding.whatsnewmoview.ui.base.adapter.LoadingRecylerViewAdapter
import com.dicoding.whatsnewmoview.ui.base.adapter.RecyclerViewItemClickListener
import com.dicoding.whatsnewmoview.ui.landingpage.detailmovie.DetailMovieActivity
import com.dicoding.whatsnewmoview.ui.landingpage.home.AdapterListMoviePaging
import com.dicoding.whatsnewmoview.util.ext.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CinemaFragment : BaseFragment() {

    private lateinit var viewModel: CinemaViewModel
    private lateinit var binding: FragmentCinemaBinding

    @Inject
    lateinit var adapterCinema: AdapterListMoviePaging

    @Inject
    lateinit var adapterLoading: LoadingRecylerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCinemaBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecylerView()
        initSwipeRefreshListener()
        viewModel.getListMovie()
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CinemaViewModel::class.java)
    }

    override fun observeChange() {
        observe(viewModel.throwable, ::onErrorHandle)
        observe(viewModel.dataMovie, ::submiteData)
    }

    private fun setRecylerView() {
        val llManager = LinearLayoutManager(activity?.applicationContext)
        binding.rvListCinema.layoutManager = llManager
        binding.rvListCinema.adapter = adapterCinema.withLoadStateFooter(
            footer = adapterLoading
        )

        adapterCinema.addLoadStateListener { loadState ->

            binding.pbLoading.isVisible = loadState.source.refresh is LoadState.Loading

            showEmptyPage(loadState.source.refresh is LoadState.NotLoading && loadState.prepend.endOfPaginationReached && adapterCinema.itemCount == 0)
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(context, it.error.message, Toast.LENGTH_LONG).show()
            }
        }

        adapterCinema.setOnclickListener(object : RecyclerViewItemClickListener<ListMovieDto> {
            override fun itemClick(item: ListMovieDto?, state: Int) {
                item?.let {
                    startActivity(DetailMovieActivity.getIntent(getBaseActivity(), it))
                }
            }
        })
    }

    private fun showEmptyPage(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvListCinema.visibility = View.GONE
            binding.emptyData.visibility = View.VISIBLE
        } else {
            binding.rvListCinema.visibility = View.VISIBLE
            binding.emptyData.visibility = View.GONE
        }
    }


    private fun submiteData(data: PagingData<ListMovieDto>) {
        binding.swipeLayout.isRefreshing = false
        lifecycleScope.launch {
            adapterCinema.submitData(data)
        }
    }

    @ExperimentalCoroutinesApi
    private fun initSwipeRefreshListener() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getListMovie()
        }
    }
}