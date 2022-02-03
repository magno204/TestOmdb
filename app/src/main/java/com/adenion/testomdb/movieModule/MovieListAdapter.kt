package com.adenion.testomdb.movieModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adenion.testomdb.R
import com.adenion.testomdb.common.entities.MovieEntity
import com.adenion.testomdb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*
import kotlin.collections.ArrayList

class MovieListAdapter(private var listener: OnClickListener): ListAdapter<MovieEntity, RecyclerView.ViewHolder>(MovieDiffCallback()), Filterable {

    private lateinit var mContext: Context
    var movieList : MutableList<MovieEntity> = mutableListOf()

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

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    submitList(movieList)
                    //currentList =
                    //countryFilterList = countryList
                } else {
                    val resultList : MutableList<MovieEntity> = mutableListOf()
                    for (row in currentList) {
                        if (row.title.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    submitList(resultList)
                    //countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = currentList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                var filterResult = results?.values as MutableList<MovieEntity>
                //countryFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

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