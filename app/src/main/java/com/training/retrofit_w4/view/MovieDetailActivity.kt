package com.training.retrofit_w4.view

import android.R
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.training.retrofit_w4.ViewModel.MovieViewModel
import com.training.retrofit_w4.adapter.GenreListAdapter
import com.training.retrofit_w4.adapter.ProductionCompanyAdapter
import com.training.retrofit_w4.adapter.SpokenLanguageAdapter
import com.training.retrofit_w4.databinding.ActivityMovieDetailBinding
import com.training.retrofit_w4.helper.Const.BASE_IMG
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.net.URL
import java.text.DecimalFormat


@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMovieDetailBinding
    private lateinit var viewModel : MovieViewModel
    private lateinit var genreAdapter : GenreListAdapter
    private lateinit var PCAdapter : ProductionCompanyAdapter
    private lateinit var spokenLangAdapter : SpokenLanguageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Movie Detail"

        binding.genreRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.PCRecylerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.spokenLanguageRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
        }

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.MD.observe(this) { response ->
            val bitmap = getBitmap(BASE_IMG + response.poster_path)
            val dominantColor : Int = getDominantColor(bitmap)
            getSupportActionBar()?.setBackgroundDrawable( ColorDrawable(dominantColor))
            setStatusBarColor(dominantColor + 10)
            binding.titleDetail.setText(response.title)
            binding.mainMovieDetail.visibility = View.VISIBLE
            binding.loadingMovieDetail.visibility = View.GONE
            binding.releaseDate.setText(response.release_date)

            binding.adultMovie.text = "For Adult only : " + if(response.adult) "Yes" else "No"
            binding.voteAVG.text = "Popularity : " + response.popularity.toString()
            binding.runtime.text = "Runtime : " + response.runtime.toString() + " Minutes"

            binding.rating.text = "Rating : " + DecimalFormat("##.##").format(response.vote_average).toString()
            Picasso.get().load(BASE_IMG + response.poster_path).into(binding.posterImg)
            genreAdapter = GenreListAdapter(response.genres, dominantColor)
            Log.d(TAG, "onCreate: ${response.spoken_languages}")
            spokenLangAdapter = SpokenLanguageAdapter(response.spoken_languages.filter{sl -> sl.english_name != ""}, dominantColor)
            binding.spokenLanguageRV.adapter = spokenLangAdapter
            PCAdapter = ProductionCompanyAdapter(response.production_companies)
            binding.genreRecyclerView.adapter = genreAdapter
            binding.PCRecylerView.adapter = PCAdapter
            binding.sypnosis.text = response.overview
            Picasso.get().load(BASE_IMG + response.backdrop_path).into(binding.backdropImg);
        }
        setVal()
    }

    fun setStatusBarColor(color : Int){
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(color)
    }

    fun getBitmap(url : String) :Bitmap?{
        try {
            val url = URL(url)
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            return image
        } catch (e: IOException) {
            System.out.println(e)
            return null
        }
    }

    fun getDominantColor(bitmap: Bitmap?, x : Int = 40, y : Int = 0): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 100, 100, true)
        var color = newBitmap.getPixel(x, y)

        newBitmap.recycle()
        if(color <= -1 && color > -100 ){
            color = getDominantColor(bitmap, x = x, y = (y+70))
        }

        return color
    }

    fun setVal(){
        val movieID = intent.getIntExtra("movie_id", -1)

        viewModel.getMovieByID(movieID)
    }


}