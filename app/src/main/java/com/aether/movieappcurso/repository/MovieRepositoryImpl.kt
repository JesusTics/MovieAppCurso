package com.aether.movieappcurso.repository

import com.aether.movieappcurso.data.model.MovieList
import com.aether.movieappcurso.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource):MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList = dataSource.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()
}