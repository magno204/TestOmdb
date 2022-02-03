package com.adenion.testomdb.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.mainModule.model.MainInteractor
import com.adenion.testomdb.movieEditModule.MovieDetailViewModel

class MainViewModel: ViewModel() {

    // Singleton
    companion object{
        @Volatile
        private var INSTANCE: MainViewModel? = null

        fun getInstance() = INSTANCE?: synchronized(this){
            INSTANCE?: MainViewModel().also { INSTANCE = it }
        }
    }

    private var interactor: MainInteractor = MainInteractor()

    private val movies = interactor.movies

    fun getMovies(): LiveData<MovieEntity>{
        return movies
    }

    // ViewModels
    var movieDetailVM: MovieDetailViewModel? = null

}