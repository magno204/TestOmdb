package com.adenion.testomdb.movieModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.adenion.testomdb.R
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentMoviesBinding
    private lateinit var mMoviesVM: MoviesViewModel
    private lateinit var mAdapter: MovieListAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMoviesVM = ViewModelProvider(requireActivity())[MoviesViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        //MVVM
        setupViewModel()

    }

    private fun setupViewModel() {

        mMoviesVM.getMovies().observe(viewLifecycleOwner) { movie ->
            mBinding.progressBar.visibility = View.GONE
            mAdapter.submitList(movie)
        }
    }

    private fun setupRecyclerView() {
        mAdapter = MovieListAdapter(this)
        mGridLayout = GridLayoutManager(context, 1)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    override fun onClick(movieEntity: MovieEntity) {

    }

}