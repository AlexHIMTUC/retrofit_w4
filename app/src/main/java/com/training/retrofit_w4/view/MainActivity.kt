package com.training.retrofit_w4.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.retrofit_w4.ViewModel.MovieViewModel
import com.training.retrofit_w4.adapter.RetrofitPageAdapter
import com.training.retrofit_w4.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : RetrofitPageAdapter
    private lateinit var viewModel : MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "List Movie"
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.getNowPlaying(1)
        viewModel.curData.observe(this) { response ->
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = RetrofitPageAdapter(response)
            binding.recyclerView.adapter = adapter
        }
    }
}