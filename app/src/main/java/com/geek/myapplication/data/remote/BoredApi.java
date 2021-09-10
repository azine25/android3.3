package com.geek.myapplication.data.remote;

import com.geek.myapplication.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BoredApi {

    @GET("/posts")
    Call<List<Post>> getAll();

    @POST("/posts/")
    Call<Post> createPost(
            @Body Post post
    );

    @DELETE("{postId}")
    Call<Post> deletePost(
            @Path("postId") String id
    );

    @PUT("{postId}")
    Call<Post> update(
            @Path("postId") String id,
            @Body Post post
    );

}
