package com.adenion.testomdb.movieEditModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.adenion.testomdb.R
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.databinding.FragmentMovieDetailBinding
import com.adenion.testomdb.mainModule.MainActivity
import com.adenion.testomdb.mainModule.viewModel.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieDetailFragment : Fragment() {

    private lateinit var mBinding: FragmentMovieDetailBinding
    private lateinit var mMainVM: MainViewModel
    private var mActivity : MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainVM = MainViewModel.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupTextFields(mMainVM.movieDetailVM!!.getMovieSelect())
    }

    private fun setupActionBar(){
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.title_movie_detail)
        setHasOptionsMenu(true)
    }

    private fun setupTextFields(movieEntity: MovieEntity) {
        with(mBinding){
            tvDirector.text = "Director: ${movieEntity.director}"
            tvGenre.text = "Género: ${movieEntity.genre}"
            tvPoster.text = movieEntity.plot
            tvReleaseDate.text = "Fecha de estreno: ${movieEntity.released}"
            tvRuntime.text = "Duración: ${movieEntity.runtime}"
            tvTitle.text = movieEntity.title
            tvYear.text = "Año: ${movieEntity.year}"

            Glide.with(requireContext())
                .load(movieEntity.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.centerCrop()
                .fitCenter()
                .into(mBinding.imgPhoto)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().supportFragmentManager.popBackStack()
                //mActivity?.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}