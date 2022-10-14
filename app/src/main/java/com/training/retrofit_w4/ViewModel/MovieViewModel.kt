package com.training.retrofit_w4.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.retrofit_w4.model.Movie
import com.training.retrofit_w4.model.MovieDetail
import com.training.retrofit_w4.model.Result
import com.training.retrofit_w4.repository.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repositor : RetrofitRepository) : ViewModel(){
    val curData : MutableLiveData<ArrayList<Result>> = MutableLiveData<ArrayList<Result>>()
    val MD : MutableLiveData<MovieDetail> = MutableLiveData<MovieDetail>()
    fun getNowPlaying(page : Int){
        viewModelScope.launch {
            repositor.getNowPlayingMovie(page).let {
                response ->
                if(response.isSuccessful){
                    curData.postValue(response.body()?.results as ArrayList<Result>)
                }else{
                    Log.e("Get Data", "Failed")
                }
            }
        }
    }

    fun getMovieByID(id : Int){
        viewModelScope.launch {
            repositor.getMovieById(id).let {
                    response ->
                if(response.isSuccessful){

                    MD.postValue(response.body() as MovieDetail)
                    Log.d(TAG, "getMovieByID: ${response.body()}")
                }else{
                    Log.e("Get Data", "Failed")
                }
            }
        }
    }
}