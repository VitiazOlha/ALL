package com.colorhills.all.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChannelRootService {
    @GET("image")
    Call<ChannelRoot> getChannel(
            @Query("apikey") String apikey,
            @Query("q") String keyword,
            @Query("output") String json,
            @Query("pageno") int count,
            @Query("result") int result);
}
