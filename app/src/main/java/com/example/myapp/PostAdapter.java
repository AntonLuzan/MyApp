package com.example.myapp;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends ListAdapter<Post, PostAdapter.PostViewHolder> {

    private final OnItemClickListener listener;

    public PostAdapter(@NonNull DiffUtil.ItemCallback<Post> diffCallback, OnItemClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
        }

        public void bind(final Post post, final OnItemClickListener listener) {
            // Делаем текст жирным и приводим первую букву заголовка к верхнему регистру
            String formattedTitle = post.getTitle().substring(0, 1).toUpperCase() + post.getTitle().substring(1);
            textViewTitle.setText(formattedTitle);
            textViewTitle.setTypeface(null, Typeface.BOLD);

            itemView.setOnClickListener(v -> listener.onItemClick(post));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    public static class PostDiffCallback extends DiffUtil.ItemCallback<Post> {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.equals(newItem);
        }
    }
}