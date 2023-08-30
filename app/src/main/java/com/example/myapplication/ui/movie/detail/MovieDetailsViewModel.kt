package com.example.myapplication.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.MovieDetails
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(
    private val movieRepo : MovieRepository
) : ViewModel(){

    private val vm_movieDetails : MutableLiveData<Resource<MovieDetails>> = MutableLiveData()
    val movieDetails : LiveData<Resource<MovieDetails>> = vm_movieDetails

    fun setImdbID(imdbID : String){
        getMovieDetails(imdbID)
    }

    private fun getMovieDetails(imdbID : String){
        viewModelScope.launch {
            movieRepo.getMovieDetails(imdbID).collect{
                vm_movieDetails.value = it
            }
        }
    }
}
