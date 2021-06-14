package com.company.myfavoritepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.core.data.model.ListMovieDto
import com.company.myfavoritepage.databinding.FavoriteFragmentBinding
import com.dicoding.whatsnewmoview.di.MyFavoriteModuleDependencies
import com.dicoding.whatsnewmoview.ui.base.BaseFragment
import com.dicoding.whatsnewmoview.ui.base.adapter.LoadingRecylerViewAdapter
import com.dicoding.whatsnewmoview.ui.base.adapter.RecyclerViewItemClickListener
import com.dicoding.whatsnewmoview.ui.landingpage.detailmovie.DetailMovieActivity
import com.dicoding.whatsnewmoview.ui.landingpage.detailserial.DetailSerialActivity
import com.dicoding.whatsnewmoview.ui.landingpage.home.AdapterListMoviePaging
import com.dicoding.whatsnewmoview.util.ext.observe
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : BaseFragment() {

    private lateinit var binding: FavoriteFragmentBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    @Inject
    lateinit var adapterSerial: AdapterListMoviePaging

    @Inject
    lateinit var adapterLoading: LoadingRecylerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecylerView()
        initSwipeRefreshListener()
        viewModel.getListFavorie()
    }

    override fun initViewModel() {
        DaggerMyFavoriteComponent.builder()
            .context(requireActivity().applicationContext)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    MyFavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun observeChange() {
        observe(viewModel.throwable, ::onErrorHandle)
        observe(viewModel.dataMovie, ::submiteData)
    }

    private fun setRecylerView() {
        val llManager = LinearLayoutManager(activity?.applicationContext)
        binding.rvListFavorite.layoutManager = llManager
        binding.rvListFavorite.adapter = adapterSerial.withLoadStateFooter(
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
                    if (it.isSerial){
                        startActivity(DetailSerialActivity.getIntent(getBaseActivity(), it))
                    } else {
                        startActivity(DetailMovieActivity.getIntent(getBaseActivity(), it))
                    }
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
            binding.rvListFavorite.visibility = View.GONE
            binding.emptyDataSerial.visibility = View.VISIBLE
        } else {
            binding.rvListFavorite.visibility = View.VISIBLE
            binding.emptyDataSerial.visibility = View.GONE
        }
    }

    @ExperimentalCoroutinesApi
    private fun initSwipeRefreshListener() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getListFavorie()
        }
    }

}