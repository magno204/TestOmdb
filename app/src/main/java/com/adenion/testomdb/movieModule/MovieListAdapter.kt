package com.adenion.testomdb.movieModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adenion.testomdb.R
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieListAdapter(private var listener: OnClickListener): ListAdapter<MovieEntity, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)

        with(holder as ViewHolder){
            setListener(movie)
            binding.tvTitle.text = movie.title
            binding.tvYear.text = movie.year.toString()
            //binding.tvCountry.text = movie.Country
            binding.tvGenre.text = movie.genre
            binding.tvRuntime.text = "Duración: ${movie.runtime}"
            binding.tvDirector.text = "Director: ${movie.director}"

            Glide.with(mContext)
                .load(movie.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemMovieBinding.bind(view)

        fun setListener(movieEntity: MovieEntity){

            with(binding.root){
                setOnClickListener {
                    listener.onClick(movieEntity)
                }

                /*setOnLongClickListener {
                    listener.onDeleteStore(storeEntity)
                    true
                }*/
            }

            /*binding.chFavorite.setOnClickListener {
                listener.onFavoriteStore(storeEntity)
            }*/
        }
    }

    class MovieDiffCallback: DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}