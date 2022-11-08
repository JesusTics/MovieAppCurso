package com.aether.movieappcurso.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.aether.movieappcurso.R
import com.aether.movieappcurso.databinding.FragmentMovieDetailBinding
import com.aether.movieappcurso.utils.AppConstants
import com.bumptech.glide.Glide

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding:FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext()).load("${AppConstants.URL_IMAGE}${args.backgrounImageUrl}").centerCrop().into(binding.imgBackground)
        Glide.with(requireContext()).load("${AppConstants.URL_IMAGE}${args.posterImageUrl}").centerCrop().into(binding.imgMovie)

        binding.txtDescription.text = args.overview
        binding.txtLanguage.text = "Language ${args.language}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.textView.text = args.title
        binding.txtReleased.text = "Released ${args.releaseDate}"

    }
}