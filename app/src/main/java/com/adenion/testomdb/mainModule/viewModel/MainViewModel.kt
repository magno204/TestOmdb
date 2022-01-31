package com.adenion.testomdb.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.mainModule.model.MainInteractor

class MainViewModel: ViewModel() {

    private var interactor: MainInteractor


    init {
        interactor = MainInteractor()
    }

    private val movies = interactor.movies

    fun getMovies(): LiveData<MovieEntity>{
        return movies
    }

}