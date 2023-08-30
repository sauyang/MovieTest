package com.example.myapplication.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.myapplication.model.Movie
import com.example.myapplication.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
@Inject
constructor(
    private val movieRepo : MovieRepository
) : ViewModel(){

    private val vm_movies : MutableLiveData<PagingData<Movie>> = MutableLiveData()
    val movies : LiveData<PagingData<Movie>> = vm_movies

    init{
        getAllMovies("marvel")
    }

    fun getAllMovies(searchText : String){

        viewModelScope.launch {
            movieRepo.getAllMovies(s = searchText, type = "movie").cachedIn(viewModelScope).collect{
                vm_movies.value = it
            }
        }
    }

}