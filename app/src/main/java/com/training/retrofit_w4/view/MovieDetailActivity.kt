package com.training.retrofit_w4.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
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
            val bitmap = getBitmap(BASE_IMG + response.backdrop_path)
            val dominantColor : Int = getDominantColor(bitmap)
            getSupportActionBar()?.setBackgroundDrawable( ColorDrawable(dominantColor))
            binding.titleDetail.setText(response.title)
            binding.mainMovieDetail.visibility = View.VISIBLE
            binding.loadingMovieDetail.visibility = View.GONE
            binding.releaseDate.setText(response.release_date)
            genreAdapter = GenreListAdapter(response.genres, dominantColor)
            spokenLangAdapter = SpokenLanguageAdapter(response.spoken_languages.filter{sl -> sl.name != null}, dominantColor)
            binding.spokenLanguageRV.adapter = spokenLangAdapter
            PCAdapter = ProductionCompanyAdapter(response.production_companies.filter { pc -> pc.logo_path != null })
            binding.genreRecyclerView.adapter = genreAdapter
            binding.PCRecylerView.adapter = PCAdapter
            binding.sypnosis.text = response.overview
            Picasso.get().load(BASE_IMG + response.backdrop_path).into(binding.backdropImg);
        }
        setVal()
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

    fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 100, 100, true)
        val color = newBitmap.getPixel(50, 75)
        newBitmap.recycle()
        return color
    }

    fun setVal(){
        val movieID = intent.getIntExtra("movie_id", -1)

        viewModel.getMovieByID(movieID)
    }


}