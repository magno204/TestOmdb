package com.adenion.testomdb.movieEditModule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adenion.testomdb.common.entities.MovieEntity

class MovieDetailViewModel : ViewModel() {

    private var movieSelect: MovieEntity = MovieEntity()

    fun setMovieSelect(movieEntity: MovieEntity){
        movieSelect = movieEntity
    }

    fun getMovieSelect(): MovieEntity {
       return movieSelect
    }

}