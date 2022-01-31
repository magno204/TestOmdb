package com.adenion.testomdb.movieModule

import com.adenion.testomdb.common.entities.MovieEntity

interface OnClickListener {
    fun onClick(movieEntity: MovieEntity)
    //fun onFavoriteStore(storeEntity: StoreEntity)
    //fun onDeleteStore(storeEntity: StoreEntity)
}