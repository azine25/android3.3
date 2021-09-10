package com.geek.myapplication.data.remote;

import android.util.Log;

import com.geek.myapplication.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostClient {
    public void createPost(Post post, CreateCallback callback) {
        RetrofitBuilder.getInstance().createPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body());
                } else Log.d("TAG", "onResponse: " + response.message());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                callback.failure(t.getLocalizedMessage());
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public interface CreateCallback {
        void success(Post post);

        void failure(String msg);
    }

    public void getPosts(GetPostCallback posts) {
        RetrofitBuilder.getInstance().getAll().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    posts.success(response.body());
                } else posts.failure(response.message());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                posts.failure(t.getLocalizedMessage());
            }
        });
    }

    public interface GetPostCallback {
        void success(List<Post> posts);

        void failure(String msg);
    }

    public void deletePost(String id, DeleteCallback callback) {
        RetrofitBuilder.getInstance().deletePost(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body());
                } else callback.failure(response.message());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                callback.failure(t.getLocalizedMessage());
            }
        });
    }

    public interface DeleteCallback {
        void success(Post post);

        void failure(String msg);
    }

    public void updatePost(String id, Post post, UpdateCallback callback) {
        RetrofitBuilder.getInstance().update(id, post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(id, post);
                } else callback.failure(response.message());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                callback.failure(t.getLocalizedMessage());
            }
        });
    }

    public interface UpdateCallback {
        void success(String id, Post post);

        void failure(String msg);
    }


}
