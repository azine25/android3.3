package com.geek.myapplication.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private RetrofitBuilder() {
    }

    private static BoredApi instance;

    public static BoredApi getInstance() {
        if (instance == null) {
            instance = createRetrofit();
        }
        return instance;
    }

    private static BoredApi createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://android-3-mocker.herokuapp.com/posts/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BoredApi.class);
    }

}
