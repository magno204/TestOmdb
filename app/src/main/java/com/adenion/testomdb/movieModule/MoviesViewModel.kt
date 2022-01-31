package com.adenion.testomdb.movieModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adenion.testomdb.common.entities.MovieEntity

class MoviesViewModel : ViewModel() {

    private var interactor: MoviesInteractor
    private var movieList: MutableList<MovieEntity>

    init {
        interactor = MoviesInteractor()
        movieList = mutableListOf()
    }

    private val movies = interactor.movies

    /*private val movies: MutableLiveData<MutableList<MovieEntity>> by lazy {
        MutableLiveData<MutableList<MovieEntity>>().also {
            loadMovies()
        }
    }*/

    private fun loadMovies() {
        /*interactor.getMovies {
            movieList = it
        }*/
    }

    fun getMovies(): LiveData<MutableList<MovieEntity>> {
        return movies
    }

}