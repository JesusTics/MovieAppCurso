package com.aether.movieappcurso.repository

import com.aether.movieappcurso.data.model.MovieList

interface MovieRepository {

    suspend fun getUpcomingMovies(): MovieList

    suspend fun getTopRatedMovies(): MovieList

    suspend fun getPopularMovies(): MovieList
}