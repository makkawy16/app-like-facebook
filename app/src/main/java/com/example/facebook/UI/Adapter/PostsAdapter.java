package com.example.facebook.UI.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook.data.Model.PostModel;
import com.example.facebook.databinding.ItemPostLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.postViewHolder> {
    List<PostModel> posts;

    public void addPost(List<PostModel> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostLayoutBinding binding =
                ItemPostLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new postViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewHolder holder, int position) {
        PostModel post = posts.get(position);
        holder.binding.profileName.setText(post.getUserName());
        holder.binding.postTitle.setText(post.getPostTitle());
        Picasso.get().load(post.getUserProfilePic()).into(holder.binding.profilePicHome);
        if (post.getUploadedPhotoURl() != null)
            Picasso.get().load(post.getUploadedPhotoURl()).into(holder.binding.myImg);

    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }


    static class postViewHolder extends RecyclerView.ViewHolder {
        ItemPostLayoutBinding binding;

        public postViewHolder(ItemPostLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
