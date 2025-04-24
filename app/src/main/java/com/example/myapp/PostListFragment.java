package com.example.myapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PostListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private PostViewModel postViewModel;
    private FavoritesManager favoritesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        favoritesManager = new FavoritesManager(requireContext());

        recyclerView = view.findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PostAdapter(new PostAdapter.PostDiffCallback(), post -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", post.getId());
            bundle.putString("postTitle", post.getTitle());
            bundle.putString("body", post.getBody());

            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_postListFragment_to_detailFragment, bundle);
        }, favoritesManager); // Добавлен `FavoritesManager`

        recyclerView.setAdapter(adapter);

        // Подписка на обновление списка постов через LiveData
        postViewModel.getPostsLiveData()
                .observe(getViewLifecycleOwner(), this::updatePosts);

        return view;
    }

    private void updatePosts(List<Post> posts) {
        adapter.submitList(posts);
    }
}