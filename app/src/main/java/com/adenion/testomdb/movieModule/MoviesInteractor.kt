package com.adenion.testomdb.movieModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.adenion.testomdb.MovieApplication
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.common.utils.Constants
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MoviesInteractor {

    val movies: LiveData<MutableList<MovieEntity>> = liveData {
        var movies = getMovies()
        emit(movies)
    }

    private suspend fun getMovies() = suspendCoroutine<MutableList<MovieEntity>> { cont ->
        val url = Constants.url
        var movieList  : MutableList<MovieEntity> = mutableListOf()
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            Log.i("MOVIES", response.toString())
            //val jsonList = response.getJSONArray("movies").toString()

            val jsonList = response.toString()
            val mutableListType = object: TypeToken<MutableList<MovieEntity>>(){}.type
            movieList = Gson().fromJson(jsonList, mutableListType)
            cont.resume(movieList)
        }, {
            it.printStackTrace()
        })
        MovieApplication.movieAPI.addToRequestQueue(jsonObjectRequest)
    }

}