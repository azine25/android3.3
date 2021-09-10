package com.geek.myapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.geek.myapplication.R;
import com.geek.myapplication.data.models.Post;
import com.geek.myapplication.data.remote.PostClient;
import com.geek.myapplication.databinding.ActivityMainBinding;
import com.geek.myapplication.ui.adapters.PostAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PostAdapter adapter;
    private Post post;
    int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new PostAdapter();
        setList();
        postClick();
        fabClick();
    }

    private void fabClick() {
        binding.fab.setOnClickListener(v -> {
            startActivityForResult(new Intent(MainActivity.this, FormActivity.class), 2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2 && data != null) {
            post = new Post();
            post.setTitle(data.getStringExtra("newTitle"));
            post.setContent(data.getStringExtra("newContent"));
            post.setUser(Integer.parseInt(data.getStringExtra("newUser")));
            post.setGroup(Integer.parseInt(data.getStringExtra("newGroup")));
            new PostClient().createPost(post, new PostClient.CreateCallback() {
                @Override
                public void success(Post post) {
                    adapter.addItem(post);
                }

                @Override
                public void failure(String msg) {
                    Toast.makeText(getBaseContext(),msg, Toast.LENGTH_LONG).show();
                }
            });
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            post = adapter.getItem(pos);
            post.setTitle(data.getStringExtra("title"));
            post.setContent(data.getStringExtra("content"));
            post.setUser(Integer.parseInt(data.getStringExtra("user")));
            post.setGroup(Integer.parseInt(data.getStringExtra("group")));
            new PostClient().updatePost(post.getId(), post, new PostClient.UpdateCallback() {
                @Override
                public void success(String id, Post post) {
                    adapter.updateItem(pos, post);
                }

                @Override
                public void failure(String msg) {
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void postClick() {
        adapter.setItemClick(new PostAdapter.ItemClick() {
            @Override
            public void onItemClick(int position, Post post) {
                pos = position;
                Intent i = new Intent(MainActivity.this, FormActivity.class);
                i.putExtra("editTitle", post.getTitle());
                i.putExtra("editContent", post.getContent());
                i.putExtra("editUser", String.valueOf(post.getUser()));
                i.putExtra("editGroup", String.valueOf(post.getGroup()));
                startActivityForResult(i, 1);
            }

            @Override
            public void onItemLongClick(String id,int position) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Удалить?")
                        .setNegativeButton("НЕТ", null)
                        .setPositiveButton("Да", (dialog, which) -> {
                            new PostClient().deletePost(id, new PostClient.DeleteCallback() {
                                @Override
                                public void success(Post post) {
                                    adapter.removeItem(position);
                                }

                                @Override
                                public void failure(String msg) {
                                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }).show();
            }
        });
    }

    private void setList() {
        new PostClient().getPosts(new PostClient.GetPostCallback() {
            @Override
            public void success(List<Post> posts) {
                adapter.setList(posts);
                binding.recMain.setAdapter(adapter);
            }

            @Override
            public void failure(String msg) {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}