package com.adenion.testomdb.movieModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.adenion.testomdb.R
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.databinding.FragmentMoviesBinding
import com.adenion.testomdb.mainModule.viewModel.MainViewModel
import com.adenion.testomdb.movieEditModule.MovieDetailFragment
import com.adenion.testomdb.movieEditModule.MovieDetailViewModel

class MoviesFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentMoviesBinding
    private lateinit var mMoviesVM: MoviesViewModel
    private lateinit var mAdapter: MovieListAdapter
    private lateinit var mGridLayout: GridLayoutManager
    private lateinit var mMainVM: MainViewModel

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
            mAdapter.movieList = movie
        }
        // detalles
        mMainVM = MainViewModel.getInstance()
        mMainVM.movieDetailVM = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
    }

    private fun setupRecyclerView() {
        mAdapter = MovieListAdapter(this)
        mGridLayout = GridLayoutManager(context, 1)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter

        }

        // Buscador
        mBinding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun launchEditFragment(movieEntity: MovieEntity){
        mMainVM.movieDetailVM!!.setMovieSelect(movieEntity)
        val fragment = MovieDetailFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.hostFragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClick(movieEntity: MovieEntity) {
        launchEditFragment(movieEntity)
    }

}
