package com.training.retrofit_w4.repository

import com.training.retrofit_w4.helper.Const.API_KEY
import com.training.retrofit_w4.network.retrofitTemplate
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val api: retrofitTemplate){
    suspend fun getNowPlayingMovie(page : Int) = api.getNowPlaying(API_KEY ,page)
    suspend fun getMovieById(id : Int) = api.getMovieByID(id, API_KEY)
}