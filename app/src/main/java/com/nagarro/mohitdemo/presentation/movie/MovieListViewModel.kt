package com.nagarro.mohitdemo.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagarro.mohitdemo.data.models.MovieDto
import com.nagarro.mohitdemo.domain.MovieRepository
import com.nagarro.mohitdemo.domain.MovieResourceState
import com.nagarro.mohitdemo.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _list = MutableStateFlow<MovieResourceState<MovieDto>>(MovieResourceState.Loading())
    val list: StateFlow<MovieResourceState<MovieDto>> = _list

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            _list.value = handleResponse(repository.getPopularMovie())
        }catch (t: Throwable){
            when(t){
                is IOException -> _list.value = MovieResourceState.Error(Constants.FAILED_TO_LOAD)
                else -> _list.value = MovieResourceState.Error(Constants.FAILED_TO_LOAD)
            }
        }
    }

    private fun handleResponse(response: MovieDto): MovieResourceState<MovieDto> {
        if (response.total_pages >0){
                return MovieResourceState.Success(response)
        }
        return MovieResourceState.Error(response.page.toString())
    }

}