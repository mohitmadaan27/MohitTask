package com.nagarro.mohitdemo.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nagarro.mohitdemo.R
import com.nagarro.mohitdemo.presentation.adapters.MovieAdapter
import com.nagarro.mohitdemo.presentation.base.BaseFragment
import com.nagarro.mohitdemo.databinding.FragmentListMovieBinding
import com.nagarro.mohitdemo.domain.MovieResourceState
import com.nagarro.mohitdemo.util.hide
import com.nagarro.mohitdemo.util.show
import com.nagarro.mohitdemo.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment: BaseFragment<FragmentListMovieBinding, MovieListViewModel>() {

    override val viewModel: MovieListViewModel by viewModels()
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        clickAdapter()
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when(resource){
                is MovieResourceState.Success -> {
                    resource.data?.let { values ->
                        binding.progressCircular.hide()
                        movieAdapter.moviesList = values.results
                    }
                }

                is MovieResourceState.Error -> {
                    binding.progressCircular.hide()
                    resource.message?.let { message ->
                        toast(getString(R.string.error_occurred))
                    }
                }

                is MovieResourceState.Loading -> {
                    binding.progressCircular.show()
                }
                else -> {}
            }

        }
    }

    private fun clickAdapter() {
        movieAdapter.setOnClickListener { movieModel ->
            toast(movieModel.title, Toast.LENGTH_SHORT)
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListMovieBinding
    = FragmentListMovieBinding.inflate(inflater, container, false)
}