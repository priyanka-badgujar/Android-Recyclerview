package com.example.recyclerview.databaseCalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("repositories")
    Call<List<TrendingApi>> getTrendingApi();
}
