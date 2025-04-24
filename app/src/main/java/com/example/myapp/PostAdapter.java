package com.example.myapp;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends ListAdapter<Post, PostAdapter.PostViewHolder> {

    private final OnItemClickListener listener;
    private final FavoritesManager favoritesManager;

    public PostAdapter(@NonNull DiffUtil.ItemCallback<Post> diffCallback, OnItemClickListener listener, FavoritesManager favoritesManager) {
        super(diffCallback);
        this.listener = listener;
        this.favoritesManager = favoritesManager;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(getItem(position), listener, favoritesManager);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        Button buttonFavorite;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            buttonFavorite = itemView.findViewById(R.id.button_favorite);
        }

        public void bind(final Post post, final OnItemClickListener listener, final FavoritesManager favoritesManager) {
            String title = post.getTitle();
            String formattedTitle = title.isEmpty() ? "Без заголовка" : title.substring(0, 1).toUpperCase() + title.substring(1);

            textViewTitle.setText(formattedTitle);
            textViewTitle.setTypeface(null, Typeface.BOLD);

            // Проверяем, добавлен ли пост в избранное
            updateFavoriteButton(post, favoritesManager);

            buttonFavorite.setOnClickListener(v -> {
                favoritesManager.toggleFavorite(post);
                updateFavoriteButton(post, favoritesManager);
            });

            itemView.setOnClickListener(v -> listener.onItemClick(post));
        }

        private void updateFavoriteButton(Post post, FavoritesManager favoritesManager) {
            buttonFavorite.setText(favoritesManager.isFavorite(post) ? "❤️ В избранном" : "⭐ В избранное");
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