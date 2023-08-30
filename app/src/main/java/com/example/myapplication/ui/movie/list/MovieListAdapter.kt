package com.example.myapplication.ui.movie.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.model.Movie
import com.example.myapplication.utils.MovieDiffCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MovieListAdapter
@Inject
constructor(
    @ApplicationContext val context : Context,
    private val clicked: (String)-> Unit
) : PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(
    MovieDiffCallback
        ){

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie?) {
            binding.tvMovieTitle.text = data?.Title

            Glide.with(binding.root)
                .load(data?.Poster)
                .placeholder(circularProgressBar())
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(binding.ivMoviePoster)

            binding.root.setOnClickListener {
                data?.imdbID?.let { id -> clicked.invoke(id) }
            }

        }

        private fun circularProgressBar(): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 10f
            circularProgressDrawable.centerRadius = 40f
            circularProgressDrawable.start()
            return circularProgressDrawable
        }
    }


}