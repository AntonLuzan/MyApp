package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DiffUtil;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts;
    private OnItemClickListener listener;

    public PostAdapter(List<Post> posts, OnItemClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    public void updatePosts(List<Post> newPosts) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(posts, newPosts));
        posts = newPosts;
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post, listener );
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
        }

        public void bind(final Post post, final OnItemClickListener listener) {
            textViewTitle.setText(post.getTitle());
            itemView.setOnClickListener(v -> listener.onItemClick(post));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }


    private static class DiffCallback extends DiffUtil.Callback {
        private final List<Post> oldList;
        private final List<Post> newList;

        public DiffCallback(List<Post> oldList, List<Post> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}