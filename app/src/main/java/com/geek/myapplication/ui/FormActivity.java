package com.geek.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.geek.myapplication.R;
import com.geek.myapplication.data.models.Post;
import com.geek.myapplication.databinding.ActivityFormBinding;

public class FormActivity extends AppCompatActivity {

    private ActivityFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setResult();
        setInfo();
    }

    private void setInfo() {
        if (getIntent() != null) {
            binding.etTitle.setText(getIntent().getStringExtra("editTitle"));
            binding.etContent.setText(getIntent().getStringExtra("editContent"));
            binding.etUser.setText(getIntent().getStringExtra("editUser"));
            binding.etGroup.setText(getIntent().getStringExtra("editGroup"));
        }
    }

    private void setResult() {
        binding.btnSend.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("newTitle", binding.etTitle.getText().toString());
            intent.putExtra("newContent", binding.etContent.getText().toString());
            intent.putExtra("newUser", binding.etUser.getText().toString());
            intent.putExtra("newGroup", binding.etGroup.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}