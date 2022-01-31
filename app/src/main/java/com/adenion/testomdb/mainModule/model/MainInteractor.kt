package com.adenion.testomdb.mainModule.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.adenion.testomdb.MovieApplication
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.common.utils.Constants
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.security.auth.callback.Callback

class MainInteractor {

    val movies: LiveData<MovieEntity> = liveData {
        var movies = getMovies()
        emit(movies)
    }

    private fun getMovies() : MovieEntity {
        val url = Constants.url
        var movie = MovieEntity()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.i("MOVIES", response.toString())
            val movieType = object: TypeToken<MovieEntity>(){}.type
            val movieGson = Gson().fromJson<MovieEntity>(response.toString(), movieType)
            movie = movieGson
        }, {
            it.printStackTrace()
        })
        MovieApplication.movieAPI.addToRequestQueue(jsonObjectRequest)
        return movie
    }
}