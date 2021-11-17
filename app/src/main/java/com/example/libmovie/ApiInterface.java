package com.example.libmovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/3/movie/{category}")
    Call<MovieResults> listOfMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/movie/{category}")
    Call<MovieResults> listOfMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET("/3/movie/{movie_id}")
    Call<MovieDetails> listOfMovies(
            @Path("movie_id") int movie_id,
            @Query("api_key") String apiKey
    );


    @GET("/3/search/{category}")
    Call<SearchClass> Search(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("/3/{category}/{person_id}")
    Call<PeopleClass> People(
            @Path("category") String category,
            @Path("person_id") int person_id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/person/{person_id}")
    Call<PeopleDetailsClass> PeopleDetails(
            @Path("person_id") int person_id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/search/{category}")
    Call<MovieResults> listOfMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("query") String query
    );


    @GET("/3/search/{category}")
    Call<PeopleClass> listOfPerson(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @POST("/3/movie/{movie_id}")
    Call<PeopleClass> listOfPerson(
            @Path("movie_id") int movie_id,
            @Query("api_key") String apiKey,
            @Query("rating") float rating
    );



}
