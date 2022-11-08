package com.aether.movieappcurso.data.remote

import com.aether.movieappcurso.data.model.MovieList
import com.aether.movieappcurso.repository.WebService
import com.aether.movieappcurso.utils.AppConstants

class MovieDataSource(private val webService: WebService) {
    suspend fun getUpcomingMovies(): MovieList {
        return webService.getUpcomingMovies(AppConstants.API_KEY)
    }

    suspend fun getTopRatedMovies(): MovieList {
        return webService.getTopRatedMovies(AppConstants.API_KEY)
    }

    suspend fun getPopularMovies(): MovieList {
        return webService.getPopularMovies(AppConstants.API_KEY)
    }
}