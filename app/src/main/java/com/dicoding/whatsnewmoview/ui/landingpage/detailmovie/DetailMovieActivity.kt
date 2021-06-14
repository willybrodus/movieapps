package com.dicoding.whatsnewmoview.ui.landingpage.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.whatsnewmoview.R
import com.company.core.data.model.DetailMovieDto
import com.company.core.data.model.ListMovieDto
import com.company.core.data.model.ProduceByDto
import com.company.core.data.model.RemoteState
import com.company.core.data.model.RemoteState.*
import com.dicoding.whatsnewmoview.databinding.ActivityDetailMovieBinding
import com.dicoding.whatsnewmoview.ui.base.BaseActivity
import com.dicoding.whatsnewmoview.ui.landingpage.AdapterProduceBye
import com.dicoding.whatsnewmoview.util.ext.observe
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMovieActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context, listMovieDto: ListMovieDto) = Intent(context, DetailMovieActivity::class.java).apply {
            putExtra(ListMovieDto::class.java.simpleName, listMovieDto)
        }
    }

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailMovieBinding
    @Inject
    lateinit var adapterProduceBy : AdapterProduceBye

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.setMovie(intent.getParcelableExtra<ListMovieDto>(
            ListMovieDto::class.java.simpleName) as ListMovieDto
        )
        initRecyleView()
        viewModel.getDetailMoview()
    }

    override fun observeChange() {
        observe(viewModel.throwable, ::onErrorHandle)
        observe(viewModel.detailMovie, ::detailViewState)
    }

    private fun detailViewState(state: RemoteState<DetailMovieDto>) {
        when (state) {
            is RemoteData -> {
                showDetailMovie(state.result)
            }
            is InProgress -> loading(state.inProgress)
        }
    }

    private fun showDetailMovie(detailMovie : DetailMovieDto){
        binding.txtRate.text = detailMovie.voteAverage.toString()
        binding.txtTitle.text = detailMovie.title
        binding.txtSubtitle.text = detailMovie.tagline
        binding.txtDate.text = detailMovie.releaseDate
        binding.txtGenre.text = detailMovie.getGenre()
        binding.desc.text = detailMovie.overview
        binding.favorite.isChecked = detailMovie.isFavorite
        adapterProduceBy.addAll(detailMovie.productionCompanies as ArrayList<ProduceByDto>)
        initCollapsingToolbar(detailMovie.title?:"")
        Glide
            .with(this)
            .load(detailMovie.backdropPath)
            .centerCrop()
            .into(binding.imageBackground)
        initLoveListener()
    }

    private fun initLoveListener(){
        binding.favorite.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                viewModel.saveToFavorite()
            } else {
                viewModel.deleteFromFavorite()
            }
        }
    }

    private fun initCollapsingToolbar(nameMovie: String) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar)
        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppbar)
        binding.collapsingToolbar.setContentScrimColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.onyx
            )
        )
        binding.collapsingToolbar.setStatusBarScrimColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.onyx
            )
        )
        binding.collapsingToolbar.title = " "

        binding.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var titleIsShowing = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = nameMovie
                    titleIsShowing = true
                } else if (titleIsShowing) {
                    binding.collapsingToolbar.title = " "
                    titleIsShowing = false
                }
            }

        })
    }

    private fun initRecyleView(){
        binding.rvCompanies.layoutManager = LinearLayoutManager(this)
        binding.rvCompanies.adapter = adapterProduceBy
    }


}