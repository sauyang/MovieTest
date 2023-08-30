package com.example.myapplication.ui.movie.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMovieDetailsBinding
import com.example.myapplication.repository.RatingsDTO
import com.example.myapplication.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentMovieDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()

        binding.ivCloseBtn.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun subscribeObservers() {

        viewModel.setImdbID(args.imdbID)
        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            when(result.status) {
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.llParent.isVisible = true
                    binding.txtError.isVisible = false
                    binding.btnRetry.isVisible = false
                    result.data?.let { movieDetails ->
                        movieDetails.Poster.let { images ->   Glide.with(binding.root)
                            .load(movieDetails.Poster)
                            .placeholder(circularProgressBar())
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .fitCenter()
                            .into(binding.ivMovie) }
                        setMovieDetailUI(
                            movieDetails.imdbRating,movieDetails.imdbVotes,movieDetails.Title,movieDetails.Year,
                            movieDetails.Genre,movieDetails.Plot,movieDetails.Ratings)
                    }
                }
                Status.ERROR -> {
                    binding.llParent.isVisible = false
                    binding.progressBar.isVisible = false
                    binding.txtError.isVisible = true
                    binding.btnRetry.isVisible = true
                    binding.txtError.text = "Check Network Connection!"
                }
            }
        }
    }

    private fun circularProgressBar(): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    fun setMovieDetailUI(
        imdbRating: String, ivIMDBVotes: String, movieTitle: String, movieYear: String, movieGenre: String,
        moviePlot: String, genres: List<RatingsDTO>?)
    {
        binding.tvIMDBRating.text = "$imdbRating / 10"
        binding.tvIMDBVotes.text = "Awards : $ivIMDBVotes Ratings"
        binding.tvMovieTitle.text = "$movieTitle ($movieYear)"
        binding.tvMovieGenre.text = "$movieGenre"
        binding.tvMoviePlot.text = "$moviePlot"

        if(genres?.size != 0 && genres != null){
            //Dynamic View
            binding.llDynamicRatings.removeAllViews()
            binding.llDynamicRatings.isVisible = true

            for (genre in genres) {

                val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view: View = inflater.inflate(R.layout.item_rating, null)

                val tvRatingTitle = view.findViewById<View>(R.id.ratingTitle) as TextView
                tvRatingTitle.setText(genre.Source.toString())
                val tvRatingScore = view.findViewById<View>(R.id.ratingScore) as TextView
                tvRatingScore.setText(genre.Value)

                binding.llDynamicRatings.addView(view)
            }
        }

    }
}