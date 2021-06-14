package com.dicoding.whatsnewmoview.ui.landingpage.detailserial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.company.core.data.model.*
import com.dicoding.whatsnewmoview.R
import com.dicoding.whatsnewmoview.databinding.ActivityDetailSerialBinding
import com.dicoding.whatsnewmoview.ui.base.BaseActivity
import com.dicoding.whatsnewmoview.ui.landingpage.AdapterProduceBye
import com.dicoding.whatsnewmoview.util.ext.observe
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailSerialActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context, listMovieDto: ListMovieDto) = Intent(context, DetailSerialActivity::class.java).apply {
            putExtra(ListMovieDto::class.java.simpleName, listMovieDto)
        }
    }

    private val viewModel: DetailSerialViewModel by viewModels()
    private lateinit var binding: ActivityDetailSerialBinding
    @Inject
    lateinit var adapterProduceBy : AdapterProduceBye

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSerialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.setSerial(intent.getParcelableExtra<ListMovieDto>(
            ListMovieDto::class.java.simpleName) as ListMovieDto
        )
        initRecyleView()
        viewModel.getDetailSerial()
    }

    override fun observeChange() {
        observe(viewModel.throwable, ::onErrorHandle)
        observe(viewModel.detailSerial, ::detailViewState)
    }

    private fun detailViewState(state: RemoteState<DetailSerialDto>) {
        when (state) {
            is RemoteState.RemoteData -> {
                showDetailMovie(state.result)
            }
            is RemoteState.InProgress -> loading(state.inProgress)
        }
    }

    private fun showDetailMovie(detailSerial : DetailSerialDto){
        binding.txtRate.text = detailSerial.voteAverage.toString()
        binding.txtTitle.text = detailSerial.name
        binding.txtSubtitle.text = detailSerial.tagline
        binding.txtDate.text = detailSerial.firstAirDate
        binding.txtGenre.text = detailSerial.getGenre()
        binding.desc.text = detailSerial.overview
        binding.favorite.isChecked = detailSerial.isFavorite
        adapterProduceBy.addAll(detailSerial.productionCompanies as ArrayList<ProduceByDto>)
        initCollapsingToolbar(detailSerial.name?:"")
        Glide
            .with(this)
            .load(detailSerial.backdropPath)
            .centerCrop()
            .into(binding.imageBackground)
        setLatestEps(detailSerial.lastEpisodeToAir)
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

    private fun setLatestEps(lastEpisodeToAir: LastEpisodeToAir?){
        binding.txtTitleEps.text = lastEpisodeToAir?.name
        binding.descEps.text = lastEpisodeToAir?.overview
        binding.txtDateEps.text = lastEpisodeToAir?.airDate
        binding.txtEps.text = getString(R.string.label_eps, lastEpisodeToAir?.episodeNumber.toString(), lastEpisodeToAir?.seasonNumber.toString())
    }

    private fun initRecyleView(){
        binding.rvCompanies.layoutManager = LinearLayoutManager(this)
        binding.rvCompanies.adapter = adapterProduceBy
    }
}