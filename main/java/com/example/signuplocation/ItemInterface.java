package com.example.signuplocation;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ItemInterface {
    @GET("weather?")
    Call<Temp> getTemp(@Query("q") String name, @Query("appid") String app_id);
}



