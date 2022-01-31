package com.adenion.testomdb

import android.app.Application
import com.adenion.testomdb.common.database.MovieAPI

class MovieApplication: Application() {
    companion object{
        lateinit var movieAPI: MovieAPI
    }

    override fun onCreate() {
        super.onCreate()
        //Volley
        movieAPI = MovieAPI.getInstance(this)
    }
}