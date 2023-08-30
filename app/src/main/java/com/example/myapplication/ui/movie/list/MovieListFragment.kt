package com.example.myapplication.ui.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MovieListFragment : Fragment() {
    val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding
    lateinit var rvAdapter: MovieListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMovieAdapter()
        subscribeObserver()
    }

    private fun initMovieAdapter() {

//        with(binding) {
//            etSearch.addTextChangedListener { editable ->
//                editable?.let {
//                    if(it.isNotEmpty()){
//                        rvAdapter.submitData(lifecycle, PagingData.empty())
//                        viewModel.getAllMovies(it.toString())
//                    }
//                }
//            }
//        }

        binding.movieRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            rvAdapter =
                MovieListAdapter(requireContext()) { imdbID: String ->
                    println("See888 " + imdbID)
                    findNavController().navigate(
                        MovieListFragmentDirections
                            .actionMovieListFragmentToMovieDetailsFragment(imdbID = imdbID)
                    )
                    //title: String -> snackBarClickedMovie(title)
                }


            adapter = rvAdapter

        }

        binding.movieRecyclerView.adapter = rvAdapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter(rvAdapter::retry)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                rvAdapter.addLoadStateListener { loadState ->
                    if (loadState.source.refresh is LoadState.Loading) {

                        if (rvAdapter.snapshot().isEmpty()) {
                            binding.progressBar.isVisible = true
                        }
                        binding.txtError.isVisible = false
                        binding.btnRetry.isVisible = false
                    } else {
                        binding.progressBar.isVisible = false

                        val error = when {
                            loadState.source.prepend is LoadState.Error -> loadState.source.prepend as LoadState.Error
                            loadState.source.append is LoadState.Error -> loadState.source.append as LoadState.Error
                            loadState.source.refresh is LoadState.Error -> loadState.source.refresh as LoadState.Error
                            else -> null
                        }
                        error?.let {
                            if (rvAdapter.snapshot().isEmpty()) {
                                binding.txtError.isVisible = true
                                binding.btnRetry.isVisible = true
                                binding.txtError.text = "Internet Connection Error!"
                            }
                        }
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                rvAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
            }
        }

        binding.btnRetry.setOnClickListener { rvAdapter.retry() }
    }

    private fun subscribeObserver() {
        viewModel.movies.observe(viewLifecycleOwner) {
            rvAdapter.submitData(lifecycle, it)
        }
    }


}