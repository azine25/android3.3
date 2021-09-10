package com.geek.myapplication.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.myapplication.data.models.Post;
import com.geek.myapplication.databinding.ItemTaskBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> list = new LinkedList<>();
    private ItemTaskBinding binding;
    private ItemClick itemClick;

    public void setList(List<Post> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Post getItem(int position){
        return list.get(position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public String returnId(int position) {
        return this.list.get(position).getId();
    }

    public void updateItem(int pos, Post post) {
        this.list.set(pos,post);
        notifyDataSetChanged();
    }

    public void addItem(Post post) {
        this.list.add(post);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemTaskBinding itemView) {
            super(itemView.getRoot());
        }

        public void onBind(Post post) {
            binding.titleView.setText(post.getTitle());
            binding.descriptionView.setText(post.getContent());

            binding.getRoot().setOnClickListener(v -> itemClick.onItemClick(getAdapterPosition(), post));
            binding.getRoot().setOnLongClickListener(v -> {
                itemClick.onItemLongClick(post.getId(), getAdapterPosition());
                return true;
            });
        }
    }

    public interface ItemClick {
        void onItemClick(int position, Post post);

        void onItemLongClick(String id,int position);
    }
}
