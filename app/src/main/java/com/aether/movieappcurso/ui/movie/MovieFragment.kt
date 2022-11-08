package com.aether.movieappcurso.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.aether.movieappcurso.R
import com.aether.movieappcurso.core.Resource
import com.aether.movieappcurso.data.model.Movie
import com.aether.movieappcurso.data.remote.MovieDataSource
import com.aether.movieappcurso.databinding.FragmentMovieBinding
import com.aether.movieappcurso.presentation.MovieViewModel
import com.aether.movieappcurso.presentation.MovieViewModelFactory
import com.aether.movieappcurso.repository.MovieRepository
import com.aether.movieappcurso.repository.MovieRepositoryImpl
import com.aether.movieappcurso.repository.RetrofitClient
import com.aether.movieappcurso.ui.movie.adapters.MovieAdapter
import com.aether.movieappcurso.ui.movie.adapters.concat.PopularConcatAdapter
import com.aether.movieappcurso.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.aether.movieappcurso.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webService)
            )
        )
    }
    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("LiveData", "Loading...")
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        "LiveData",
                        "Upcoming: ${result.data.first} \n \n Top Rated: ${result.data.second} \n \n Popular: ${result.data.third}"
                    )

                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    Log.d("ERROR", "${result.exception}")
                }
            }
        })


    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count.toInt(),
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}