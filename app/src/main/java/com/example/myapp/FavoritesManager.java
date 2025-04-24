package com.example.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class FavoritesManager {
    private static final String PREF_NAME = "favorites_pref";
    private static final String KEY_FAVORITES = "favorites";

    private SharedPreferences sharedPreferences;

    public FavoritesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void toggleFavorite(Post post) {
        Set<String> favorites = sharedPreferences.getStringSet(KEY_FAVORITES, new HashSet<>());
        Set<String> updatedFavorites = new HashSet<>(favorites);

        if (favorites.contains(String.valueOf(post.getId()))) {
            updatedFavorites.remove(String.valueOf(post.getId()));
        } else {
            updatedFavorites.add(String.valueOf(post.getId()));
        }

        sharedPreferences.edit().putStringSet(KEY_FAVORITES, updatedFavorites).apply();
    }

    public boolean isFavorite(Post post) {
        Set<String> favorites = sharedPreferences.getStringSet(KEY_FAVORITES, new HashSet<>());
        return favorites.contains(String.valueOf(post.getId()));
    }

    public List<Post> getFavoritePosts(List<Post> allPosts) {
        Set<String> favorites = sharedPreferences.getStringSet(KEY_FAVORITES, new HashSet<>());
        List<Post> favoritePosts = new ArrayList<>();

        for (Post post : allPosts) {
            if (favorites.contains(String.valueOf(post.getId()))) {
                favoritePosts.add(post);
            }
        }

        return favoritePosts;
    }
}