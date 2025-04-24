package com.example.myapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private FavoritesManager favoritesManager;
    private PostViewModel postViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesManager = new FavoritesManager(requireContext());
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PostAdapter(new PostAdapter.PostDiffCallback(), post -> {}, favoritesManager);
        recyclerView.setAdapter(adapter);

        // Наблюдаем за всеми постами и фильтруем только избранные
        postViewModel.getPostsLiveData().observe(getViewLifecycleOwner(), allPosts -> {
            if (allPosts != null) {
                adapter.submitList(favoritesManager.getFavoritePosts(allPosts));
            }
        });

        return view;
    }
}